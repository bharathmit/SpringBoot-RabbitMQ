function validateFloatKeyPress(el, evt) {
		    var charCode = (evt.which) ? evt.which : event.keyCode;
		    var number = el.value.split('.');
		    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		        return false;
		    }
		    //just one dot (thanks ddlab)
		    if(number.length>1 && charCode == 46){
		         return false;
		    }
		    //get the carat position
		    var caratPos = getSelectionStart(el);
		    var dotPos = el.value.indexOf(".");
		    if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
		        return false;
		    }
		    return true;
		}

		//thanks: http://javascript.nwbox.com/cursor_position/
		function getSelectionStart(o) {
		    if (o.createTextRange) {
		        var r = document.selection.createRange().duplicate()
		        r.moveEnd('character', o.value.length)
		        if (r.text == '') return o.value.length
		        return o.value.lastIndexOf(r.text)
		    } else return o.selectionStart
		}