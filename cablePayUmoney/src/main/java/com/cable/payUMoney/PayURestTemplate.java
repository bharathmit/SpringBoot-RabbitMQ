package com.cable.payUMoney;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


public class PayURestTemplate {
	
	private String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	
	
	private String key;
	private String salt;
	private String surl;
	private String furl;
	private String api;
	private String provider;
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	public PayURestTemplate(String merchantKey, String merchantKeySalt, Map<String, String> config) {
        this.key=merchantKey;
        this.salt=merchantKeySalt;
        this.api=config.get("api");
        this.provider=config.get("provider");
        this.surl=config.get("surl");
        this.furl=config.get("furl");
        
    }
	
	
	public String hashCal(String algorithmType, PaymentBuilder paymentBuilder) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		StringBuffer hexString = new StringBuffer();
		try {
			
			String hash="";
			String[] hashVarSeq = hashSequence.split("\\|");
			
			for (String part : hashVarSeq) {
				Field field = paymentBuilder.getClass().getDeclaredField(part);    
				field.setAccessible(true);
				Object value = field.get(paymentBuilder);
				if(StringUtils.isEmpty(value)){
					value="";
				}
				hash = hash + value+"|";
			}
			
			hash = hash.concat(this.salt);
			byte[] hashseq = hash.getBytes();
			MessageDigest algorithm = MessageDigest.getInstance(algorithmType);
			algorithm.reset();
            algorithm.update(hashseq);
            
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	
	
	
	public PaymentReturn createPayment(PaymentBuilder paymentBuilder){
		try{
			paymentBuilder.setKey(this.key);
			paymentBuilder.setSurl(this.surl);
			paymentBuilder.setFurl(this.furl);
			paymentBuilder.setService_provider(this.provider);
			paymentBuilder.setHash(hashCal("SHA-512",paymentBuilder));
			
			html(paymentBuilder);
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			
			ResponseEntity<String> response = restTemplate.postForEntity(this.api+"/_payment",mapper.writeValueAsString(paymentBuilder), String.class);
			
			String responseBody = response.getBody();
			System.out.println(responseBody);
			
			return objectMapper.readValue(responseBody, PaymentReturn.class);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void html(PaymentBuilder paymentBuilder){
		// build HTML code
		        String htmlResponse = "<html> <body> \n"
		                + "      \n"
		                + "  \n"
		                + "  <h1>PayUForm </h1>\n"
		                + "  \n" + "<div>"
		                + "        <form id=\"payuform\" action=\"" + this.api+"/_payment" + "\"  name=\"payuform\" method=POST >\n"
		                + "      <input type=\"hidden\" name=\"key\" value=" + paymentBuilder.getKey()+ ">"
		                + "      <input type=\"hidden\" name=\"hash\" value=" + paymentBuilder.getHash() + ">"
		                + "      <input type=\"hidden\" name=\"txnid\" value=" + paymentBuilder.getTxnid() + ">"
		                + "      <table>\n"
		                + "        <tr>\n"
		                + "          <td><b>Mandatory Parameters</b></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "         <td>Amount: </td>\n"
		                + "          <td><input name=\"amount\" value=" + paymentBuilder.getAmount() + " /></td>\n"
		                + "          <td>First Name: </td>\n"
		                + "          <td><input name=\"firstname\" id=\"firstname\" value=" + paymentBuilder.getFirstname() + " /></td>\n"
		                + "        <tr>\n"
		                + "          <td>Email: </td>\n"
		                + "          <td><input name=\"email\" id=\"email\" value=" + paymentBuilder.getEmail() + " /></td>\n"
		                + "          <td>Phone: </td>\n"
		                + "          <td><input name=\"phone\" value=" + paymentBuilder.getPhone() + " ></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Product Info: </td>\n"
		                + "<td><input name=\"productinfo\" value=" + paymentBuilder.getProductinfo() + " ></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Success URI: </td>\n"
		                + "          <td colspan=\"3\"><input name=\"surl\"  size=\"64\" value=" + paymentBuilder.getSurl() + "></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Failure URI: </td>\n"
		                + "          <td colspan=\"3\"><input name=\"furl\" value=" + paymentBuilder.getFurl() + " size=\"64\" ></td>\n"
		                + "        </tr>\n"
		                + "\n"
		                + "        <tr>\n"
		                + "          <td colspan=\"3\"><input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" /></td>\n"
		                + "        </tr>\n"
		                + "             <tr>\n"
		                + "          <td><b>Optional Parameters</b></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Last Name: </td>\n"
		                + "          <td><input name=\"lastname\" id=\"lastname\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "          <td>Cancel URI: </td>\n"
		                + "          <td><input name=\"curl\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Address1: </td>\n"
		                + "          <td><input name=\"address1\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "          <td>Address2: </td>\n"
		                + "          <td><input name=\"address2\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>City: </td>\n"
		                + "          <td><input name=\"city\" value=" + paymentBuilder.getLastname() + "></td>\n"
		                + "          <td>State: </td>\n"
		                + "          <td><input name=\"state\" value=" + paymentBuilder.getLastname() + "></td>\n"
		                + "        </tr>\n"
		                + "        <tr>\n"
		                + "          <td>Country: </td>\n"
		                + "          <td><input name=\"country\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "          <td>Zipcode: </td>\n"
		                + "          <td><input name=\"zipcode\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "        </tr>\n"
		                + "          <td>UDF1: </td>\n"
		                + "          <td><input name=\"udf1\" value=" + paymentBuilder.getLastname() + "></td>\n"
		                + "          <td>UDF2: </td>\n"
		                + "          <td><input name=\"udf2\" value=" + paymentBuilder.getLastname() + "></td>\n"
		                + " <td><input name=\"hashString\" value=" + paymentBuilder.getLastname() + "></td>\n"
		                + "          <td>UDF3: </td>\n"
		                + "          <td><input name=\"udf3\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "          <td>UDF4: </td>\n"
		                + "          <td><input name=\"udf4\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "          <td>UDF5: </td>\n"
		               + "          <td><input name=\"udf5\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                 + "          <td>PG: </td>\n"
		               + "          <td><input name=\"pg\" value=" + paymentBuilder.getLastname() + " ></td>\n"
		                + "        <td colspan=\"4\"><input type=\"submit\" value=\"Submit\"  /></td>\n"
		                + "      \n"
		                + "    \n"
		                + "      </table>\n"
		                + "    </form>\n"
		                + " <script> "
		                + " document.getElementById(\"payuform\").submit(); "
		                + " </script> "
		                + "       </div>   "
		                + "  \n"
		                + "  </body>\n"
		                + "</html>";
		// return response
		        System.out.println(htmlResponse);
	}

}
