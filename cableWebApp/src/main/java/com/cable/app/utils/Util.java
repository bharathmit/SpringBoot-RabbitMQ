package com.cable.app.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Util implements Serializable {

    private static final long serialVersionUID = -3587428331841278259L;

    // public static Properties prop;
    private static DecimalFormat df = new DecimalFormat("0.########");
    public static Map<Character, Double> unitTable = new HashMap<Character, Double>();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfwithtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Util instance = null;
    @Getter
    @Setter
    public static Properties prop;

    static {
        unitTable.put('M', 1d);
        unitTable.put('G', 1000d);
        unitTable.put('K', 1000000d);
    }

    private Util() {

    }

    public static Util getInstance() {
        if (instance == null)
            instance = new Util();
        return instance;
    }

    public static Double fix(Double inp) {
        if (inp == null)
            return null;
        return new BigDecimal(inp).setScale(10, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static int countDecimal(Double d) {
        if (d == null)
            return 0;

        String val = df.format(d);
        int index = val.indexOf('.');
        return index < 0 ? 0 : (val.length() - index - 1);
    }

    public static Double trunc(Double obj, double d) {
        if (obj == null)
            return null;
        int digits = countDecimal(d);

        long l = (long) Math.pow(10, digits);
        long q = (long) (Util.fix(obj) * l);
        long h = q;
        return (double) h / l;
    }

    public static Double trunc(Double obj) {
        if (obj == null)
            return null;

        return obj;
        //
        // long q=(long) (obj*100000000l);
        // long h=q;
        // return (double)h/100000000;

    }

    public static double conv(double inp, String from, String to) {
        double f = unitTable.get(from.toUpperCase().charAt(0));
        double mg = inp * f;
        double t = unitTable.get(to.toUpperCase().charAt(0));
        return fix(mg / t);
    }

    public static void addInfo(String str) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, str, ""));
        // log.info(str);
    }

    public static void addWarn(String str) {
        try {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, str, " "));
        } catch (Exception swallow) {
        }
        // log.warn(str);
    }

    public static void addError(String str) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, str, ""));
        // log.error(str);
    }

    public static void addFatal(String str) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, str, ""));
        // log.fatal(str);
    }

    public static String redirect(String page) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getBeginingOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getBeginingOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getBeginingOfLastYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getStartOfWeek(Date date, int noOfDays) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, noOfDays);

        Calendar now = Calendar.getInstance();
        now.setTime(cal.getTime());

        Date[] days = new Date[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; // add 1 if
                                                                 // your week
                                                                 // start on
                                                                 // monday
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = now.getTime();
            now.add(Calendar.DAY_OF_MONTH, 1);
        }

        return getBeginingOf(days[0]);
    }

    public static Date getEndOfWeek(Date date, int noOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, noOfDays);

        Calendar now = Calendar.getInstance();
        now.setTime(cal.getTime());

        Date[] days = new Date[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1; // add 1 if
                                                                 // your week
                                                                 // start on
                                                                 // sunday
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = now.getTime();
            now.add(Calendar.DAY_OF_MONTH, 1);
        }

        return getEndOf(days[6]);
    }

    public static Date getEndOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        // calendar.set(Calendar.DAY_OF_MONTH,
        // calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        // calendar.set(Calendar.DAY_OF_WEEK, 6);
        // calendar.set(Calendar.DAY_OF_MONTH, 12);
        // calendar.set(Calendar.HOUR_OF_DAY, 23);
        // calendar.set(Calendar.MINUTE, 59);
        // calendar.set(Calendar.SECOND, 59);
        // calendar.set(Calendar.MILLISECOND, 999);
        System.out.println("Date=" + calendar.getTime());
        return calendar.getTime();
    }

    public static Date getEndOfLastYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        // calendar.set(Calendar.DAY_OF_MONTH,
        // calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        // calendar.set(Calendar.DAY_OF_WEEK, 6);
        // calendar.set(Calendar.DAY_OF_MONTH, 12);
        // calendar.set(Calendar.HOUR_OF_DAY, 23);
        // calendar.set(Calendar.MINUTE, 59);
        // calendar.set(Calendar.SECOND, 59);
        // calendar.set(Calendar.MILLISECOND, 999);
        System.out.println("Date=" + calendar.getTime());
        return calendar.getTime();
    }

    public static Date getEndOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getBeginingOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        // calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        // calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getBeginingOfMonth(Date date, int noOfMonthIncrease) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.MONTH, noOfMonthIncrease);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfMonth(Date date, int noOfMonthIncrease) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        calendar.add(Calendar.MONTH, noOfMonthIncrease);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static List<Date> getFacisalQuarter(Date date, Date fiscalDate) {

        Calendar start = Calendar.getInstance();

        List<Date> lt = getFiscalYear(date, fiscalDate);
        if (lt == null || lt.size() == 0)
            return null;
        start.setTime(lt.get(0));

        Calendar end = Calendar.getInstance();
        end.setTime(getEndOfMonth(start.getTime(), 2));
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Date> list = new ArrayList<Date>();

        for (int i = 0; i < 4; i++) {
            if (date.after(start.getTime()) && date.before(end.getTime())) {
                list.add(start.getTime());
                list.add(end.getTime());
                return list;
            }

            start.add(Calendar.MONTH, 3);
            end.add(Calendar.MONTH, 3);
            end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        return null;
    }

    public static List<Date> getFiscalYear(Date date, Date fiscalDate) {

        List<Date> list = new ArrayList<Date>();

        Calendar start = Calendar.getInstance();
        start.setTime(fiscalDate);

        Calendar end = Calendar.getInstance();
        end.setTime(fiscalDate);
        end.add(Calendar.MONTH, 11);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        if (date.after(start.getTime()) && date.before(end.getTime())) {
            list.add(start.getTime());
            list.add(end.getTime());
        } else {
            addWarn("Current Date not between Fiscal Date.");
        }

        return list;
    }

    public static List<Date> getPreviousFiscalYear(Date fiscalDate) {

        List<Date> list = new ArrayList<Date>();

        Calendar start = Calendar.getInstance();
        start.setTime(fiscalDate);
        start.add(Calendar.YEAR, -1);

        Calendar end = Calendar.getInstance();
        end.setTime(fiscalDate);
        end.add(Calendar.MONTH, 11);
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.add(Calendar.YEAR, -1);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);

        list.add(start.getTime());
        list.add(end.getTime());

        return list;
    }

    // public static Object getBean(String beanName){
    // FacesContext context = FacesContext.getCurrentInstance();
    // return context.getELContext()
    // .getELResolver().getValue(context.getELContext(), null, beanName);
    //
    // }

    public static void setSessionValue(String key, Object value) {
        ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extCon.getSession(true);

        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    public static Object getSessionValue(String key) {
        ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extCon.getSession(false);

        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    public static void clearSession() {
        ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extCon.getSession(false);

        if (session != null) {
            session.invalidate();
            // log.info("Removed old session");
        }

        // log.info("Session null");
    }

    public static String decFormat(Double inp) {
        if (inp == null)
            return "";
        DecimalFormat dfmt = new DecimalFormat();
        dfmt.setMinimumFractionDigits(1);
        dfmt.setMaximumFractionDigits(8);
        dfmt.setGroupingUsed(false);
        return dfmt.format(inp);
    }

    public static String currencyFormat(Double cur) {
        DecimalFormat dfmt = new DecimalFormat("#0.00");
        return dfmt.format(cur);
    }

    public static String trunc(String val) {
        if (val == null)
            return "";
        else
            return val;
    }

    public static String trimAll(String inp) {
        return (inp == null) ? "" : inp.trim().replaceAll("\\s+", " ");
    }

    public static String formQueryString(String[] keys, String[] values) {
        String qstr = "";
        for (int i = 0; i < keys.length; i++) {
            qstr += (qstr == "") ? "?" : "&";
            try {
                qstr += URLEncoder.encode(keys[i], "UTF-8");
                qstr += "=";
                qstr += URLEncoder.encode(values[i], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return qstr;
    }

    public static String sendSms(String to, String message) {
        String status = "";
        String strurl = "http://optin.smsalertservices.in/api/web2sms.php";
        strurl += formQueryString(new String[] { "workingkey", "sender", "to", "message" }, new String[] {
                "5052q18n2r41a832j583", "TNOMNI", to, message });
        System.out.println("---------" + strurl);
        try {
            URL url = new URL(strurl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                status += line;
            }
            reader.close();

        } catch (Exception e) {
            // log.error(e.getMessage());
        }
        return status;
    }

    public static String sendSms(String user, String password, String from, String to, String message) {
        String status = "";
        String strurl = "http://bulksms.spry2sms.com/pushsms.php";
        strurl += formQueryString(new String[] { "username", "password", "sender", "to", "message" }, new String[] {
                user, password, from, to, message });

        try {
            URL url = new URL(strurl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                status += line;
            }
            reader.close();

        } catch (Exception e) {
            // log.error(e.getMessage());
        }
        return status;
    }

    /*
     * public static String sendSms(String to,String message){ return
     * sendSms("finatel","FINAiagentpal0111","AgentPal",to,message); }
     */

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(Object str) {
        return (str == null || (str instanceof String) == false || ((String) str).trim().length() == 0);
    }

    public static String getTimeduration(Date startdate, Date enddate) {
        // log.info("created time "+startdate);
        // log.info("upto now "+enddate);
        Date date1 = startdate;
        Date date2;
        date2 = enddate;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        diff = diff % (24 * 60 * 60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        diff = diff % (60 * 60 * 1000);
        long diffMinutes = diff / (60 * 1000);
        String days = (diffDays == 0) ? "" : Long.toString(diffDays) + " days ";
        String hours = (diffHours == 0) ? "" : Long.toString(diffHours) + " Hours ";
        String minites = (diffMinutes == 0) ? "" : Long.toString(diffMinutes) + " Minutes ";
        return days + hours + minites;
    }

    public static Long getDateduration(Date startdate, Date enddate) {
        Date date1 = startdate;
        Date date2;
        date2 = enddate;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static String convUnit(String unit) {
        if (unit.equals("KG"))
            return "kg";
        else if (unit.equals("MG"))
            return "mg";

        return "g";
    }

    public static String truncate(Double obj) {
        DecimalFormat myFormatter = new DecimalFormat("#");
        return myFormatter.format(obj);
    }

    public static String getGpsLocation(String lat, String lon) {
        String address;
        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse("http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + lat + "," + lon
                    + "&sensor=true");

            NodeList nodes = doc.getElementsByTagName("GeocodeResponse");
            Element element = (Element) nodes.item(0);

            NodeList n0 = element.getElementsByTagName("status");
            Element status = (Element) n0.item(0);

            if (status.getFirstChild().getTextContent().equals("OK")) {

                NodeList nl = element.getElementsByTagName("result");
                Element result = (Element) nl.item(0);

                NodeList n2 = result.getElementsByTagName("formatted_address");
                Element location = (Element) n2.item(0);

                address = location.getFirstChild().getTextContent();
                System.out.println("GPS address:" + address);
            } else
                address = "Not found";
        }

        catch (Exception e) {
            e.printStackTrace();
            address = "Unable to get address";
        }
        return address;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    public static Date getCurrentTime(Date date, String timeZoneId) {
        if (date == null)
            return null;
        TimeZone zone = TimeZone.getTimeZone(timeZoneId);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Put your
                                                                // pattern here
        format.setTimeZone(zone);
        String text = format.format(date);
        try {
            return sdf.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getCurrentTime(Date date) {
        if (date == null)
            return null;
        String curZone = getTimeZone();
        // log.info("Time Zone: "+curZone);
        if (curZone == null)
            return date;
        TimeZone zone = TimeZone.getTimeZone(curZone);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzzz"); // Put
                                                                              // your
                                                                              // pattern
                                                                              // here

        format.setTimeZone(zone);
        // log.info("format time zone:"+format.getTimeZone().getDisplayName());
        String text = format.format(date);
        // log.info("text :"+text);
        try {
            format.setTimeZone(zone);
            Date date2 = format.parse(text);

            // log.info("date :"+date2);

            return format.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getCurrentTimeDetails(Date date) {
        if (date == null)
            return null;
        TimeZone zone = TimeZone.getTimeZone(getTimeZone());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Put
                                                                         // your
                                                                         // pattern
                                                                         // here
        format.setTimeZone(zone);
        String text = format.format(date);
        try {
            return sdfwithtime.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // public static CustomerUser whoAmI(){
    // return (CustomerUser) getSessionValue("UserObject");
    // }
    //
    // public static AdminUser getAdminUser(){
    // return (AdminUser) getSessionValue("AdminUserObject");
    // }

    public static String getCurrency() {
        return (String) getSessionValue("Currency");
    }

    public static String getTimeZone() {
        return (String) getSessionValue("TimeZone");
    }

    // public static Long getCustomerID() {
    // if(whoAmI()==null || whoAmI().getCustomer()==null)
    // return null;
    // Customer customer = whoAmI().getCustomer();
    // return customer.getId();
    // }

    public static String ObjectToString(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    public static Long ObjectToLong(Object obj) {
        return obj != null ? Long.parseLong(obj.toString()) : null;
    }

    public static Double ObjectToDouble(Object obj) {
        return obj != null ? Double.parseDouble(obj.toString()) : null;
    }

    public static Date ObjectToDate(Object obj) {
        return obj != null ? (Date) obj : null;
    }

    public static Integer ObjectToInteger(Object obj) {
        return obj != null ? Integer.parseInt(obj.toString()) : null;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isMobileValid(String mobile) {
        boolean isValid = false;
        String expression = "|[+,-]?[0-9]{5,20}";
        CharSequence inputStr = mobile;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static <E, F> F copyToProxy(E original, F proxy) {
        try {
            BeanUtils.copyProperties(original, proxy);
        } catch (Exception e) {
            // log.error("Exp", e);
        }
        return proxy;
    }

    // public static int
    // getCurrentFinancialYear(com.finateltech.resto.domain.BillSequence seq){
    // Date dt=new Date();
    // int cutoffMonth=seq.getFiscialPeriod();
    // int currentMonth=dt.getMonth()+1;
    // int currentYear=dt.getYear();
    // if(currentMonth>=cutoffMonth)
    // return currentYear;
    // return currentYear-1;
    // }

    // public static String generateRandom()
    // {
    // return Random.randomAlphaNumericString ( 5 );
    // }

    // public static Repot generateReport(Report report, Map<String,String>
    // filterMap, String generateFrom, Long userId, Long scheduleReportId){
    //
    //
    // for(ReportQuery rq : report.getQuerys()){
    //
    // rq.setColumnHeaders(Arrays.asList(rq.getColumnName().split(",")));
    // rq.setNoOfColumnHeader(rq.getColumnHeaders().size());
    //
    // //Display alignment left, right, center
    // rq.setAlignPositions(Arrays.asList(rq.getAlignPosition().split(",")));
    //
    // //Sorting String, double, integer
    // rq.setSortObjectTypes(Arrays.asList(rq.getSortObjectType().split(",")));
    //
    // //filter column in download
    // rq.setSelectedColumnHeader(new DualListModel<String>(new
    // ArrayList<String>(), rq.getColumnHeaders()));
    //
    // log.info("columnName"+rq.getColumnName());
    // log.info("SortObjectType"+rq.getSortObjectType());
    // log.info("AllignPosition"+rq.getAlignPosition());
    //
    // //downloading column rearrange
    // rq.setColumnOrderIndexs(new HashMap<String, Integer>());
    // log.info("getcolumnorderindex  "+rq.getColumnOrderIndex());
    // for(String st : rq.getColumnOrderIndex().split(",")){
    // log.info("inside generateReport");
    // log.info("rt:"+st);
    //
    // Integer index = Integer.parseInt(st);
    // rq.getColumnOrderIndexs().put(rq.getColumnHeaders().get(index), index);
    // }
    //
    //
    // String query = rq.getQuery();
    // log.info("report query value  "+query);
    //
    // for(ReportFilter rf : report.getFilters()){
    // if(filterMap.get(rf.getFilter().getParameter())!=null){
    //
    // query = query.replace(rf.getFilter().getParameter(),
    // filterMap.get(rf.getFilter().getParameter()));
    // }
    // }
    //
    // //Customer Id mapped to query
    // query = query.replace("{0}", filterMap.get("{0}"));
    //
    // //Customer branch mapped to query
    // if(report.isBranchNeed())
    // query = query.replace("{4}", filterMap.get("{4}"));
    //
    // //Customer branch Location Id mapped to query
    // if(report.isLocationNeed())
    // query = query.replace("{5}", filterMap.get("{5}"));
    //
    // rq.setColumnValues(Dao.getInstance().getReportQuery(query));
    // }
    //
    //
    // RecentReportVisit recent = new RecentReportVisit();
    // recent.setCustomerUserId(userId);
    // recent.setDate(new Date());
    // recent.setReport(report);
    // recent.setGenerateFrom(generateFrom);
    // recent.setScheduleReportId(scheduleReportId);
    // Dao.getInstance().saveorUpdate(recent);
    //
    // return report;
    // }

    // public static boolean sendMailWithAttachment(String to,String
    // subject,String message,String fileName, byte[] excel, byte[] pdf) {
    // try {
    //
    // ReportMailService service = ReportMailService.getReportMailService();
    // log.info("service :"+service);
    // log.info("reportmail sender :"+service.reportMailSender);
    // MimeMessage msg =service.reportMailSender.createMimeMessage();
    //
    // msg.setText(message);
    // msg.setContent(message,"text/html");
    // msg.setSubject(subject);
    //
    // List<InternetAddress> arr=new ArrayList<InternetAddress>();
    // for(String str:to.split(","))
    // arr.add(new InternetAddress(str));
    //
    // msg.addRecipients(Message.RecipientType.TO, arr.toArray(new
    // Address[arr.size()]));
    //
    // msg.setSentDate(new Date());
    //
    // MimeBodyPart messagePart = new MimeBodyPart();
    //
    // messagePart.setContent(message,"text/html");
    //
    // Multipart multipart = new MimeMultipart();
    // multipart.addBodyPart(messagePart);
    //
    // if(excel!=null){
    // MimeBodyPart attachment = new MimeBodyPart();
    // attachment.setFileName(fileName+".xls");
    // attachment.setContent(excel, "application/3xls");
    // multipart.addBodyPart(attachment);
    // }
    // if(pdf!=null){
    // MimeBodyPart attachment = new MimeBodyPart();
    // attachment.setFileName(fileName+".pdf");
    // attachment.setContent(pdf, "application/3pdf");
    // multipart.addBodyPart(attachment);
    // }
    //
    // msg.setContent(multipart);
    //
    // service.reportMailSender.send(msg);
    //
    // } catch (Exception mex) {
    // log.error("Error",mex);
    // return false;
    // }
    //
    // return true;
    // }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    // public static Boolean sendWelcomeMail(String email,String
    // password,Customer customer){
    // Map<String, String> requestDetails = new HashMap<String, String>();
    // EmailTemplate emailTempalte = new EmailTemplate();
    // EmailService emailService = EmailService.getEmailService();
    // String description = "";
    // try {
    // List<EmailTemplate> emailTempaltes = Dao.getInstance().getAll(
    // EmailTemplate.class, Restrictions.eq("module",
    // "Welcome Email to Customer"), null,
    // null);
    // if (emailTempaltes == null || emailTempaltes.size() <= 0) {
    // log.info("Email template not configured for this template name :Welcome Email to Customer");
    // return false;
    // }
    // emailTempalte = emailTempaltes.get(0);
    // if (!emailTempalte.isActive()) {
    // log.info("Email template has been blocked for this template name :+str");
    // return false;
    // }
    // requestDetails.put("\\$Name",customer.getName());
    // requestDetails.put("\\$User_Password",password );
    // requestDetails.put("\\$User_Email_Id", email);
    //
    // description = emailTempalte.getDescription();
    // String url=Application.prop.getProperty("domain_address");
    // description = description.replaceAll("\\{DOMAIN_ADDRESS\\}",url);
    //
    // for (String key : requestDetails.keySet()) {
    // description = description.replaceAll(key,
    // requestDetails.get(key));
    //
    // }
    // } catch (Exception e) {
    // log.error("Exception while sending mail", e);
    // return false;
    // }
    //
    // EmailTask et = new EmailTask(null, email,
    // emailTempalte.getCcList(), "USER",
    // emailTempalte.getSubject(), description,
    // EmailTask.Priority.HIGH);
    //
    // if (emailService.asyncSend(et) != null){
    // log.info("Email task initiated successfully");
    // return true;
    // }
    //
    // return false;
    // }

    public static int daysBetweenDates(Date date1, Date date2) {
        double DAY_MILLIS = 1000.0 * 24.0 * 60.0 * 60.0;
        return (int) ((date1.getTime() - date2.getTime()) / DAY_MILLIS);

    }

    public static Double formatDoubleValue(Double val) {
        // log.info("converter called");
        if (val == null)
            return null;
        return new BigDecimal(val).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        /*
         * DecimalFormat d=new DecimalFormat("#.##");
         * 
         * return Double.parseDouble(d.format(val));
         */
        /*
         * DecimalFormat decimal=new DecimalFormat("###.#");
         * log.info("values is"+val); return
         * Double.parseDouble(decimal.format(val));
         */

    }

    public static Double roundOfBillAmount(Double amount, Double fraction) {

        return (Math.round(amount * (1 / fraction)) / (1 / fraction));
    }

    public String getPropValues(String value) throws IOException {

        if (prop == null)
            getInstance().load();
        return prop.getProperty(value);

    }

    public void load() throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Application.properties");
        prop = new Properties();
        prop.load(inputStream);
    }

}
