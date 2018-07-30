
function InstaReaderTableResize() {
	var iframe = document.getElementById("iframe");
	if (iframe !== null) {
		try {
			var iDoc = iframe.contentWindow.document;
			var iHead = iframe.contentWindow.document.head;
			if (iDoc !== null) {
				var ifrH = iDoc.body.offsetHeight;
				ifrH += 20;
				if (ifrH < 380) {
					ifrH = 380;
				}
				iframe.style.height = ifrH + "px";
			}
			
			if (iHead !== null) {
				var style = document.createElement('style'),
						css = '.blogpage{background:#2b3036;color:#eee;padding:1px 0 0;margin:0;min-height:10px;-moz-box-shadow:inset 0 2px 3px -2px rgba(20,21,22,1);-webkit-box-shadow:inset 0 2px 3px -2px rgba(20,21,22,1);box-shadow:inset 0 2px 3px -2px rgba(20,21,22,1)}.blogpage>a{display:inline-block;width:50%;padding:0;margin:0;color:#c2c4c5;text-decoration:none;vertical-align:top}.blogpage>a>div{border-bottom:solid 0 #171a1d!important}.blogpage>a>div span{display:block!important;width:100%;font-size:12px!important;color:#c2c4c5;padding:3px!important;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;text-shadow:0 1px 0 #171717}.blogpage>a>div span:last-child{min-height:50px}.blogpage>a>div>span>img{width:100%!important;-moz-box-shadow:0 0 2px 0 rgba(20,21,22,1);-webkit-box-shadow:0 0 2px 0 rgba(20,21,22,1);box-shadow:0 0 2px 0 rgba(20,21,22,1);-webkit-border-radius:2px;-moz-border-radius:2px;border-radius:2px}';
				style.type = 'text/css';
				if (style.styleSheet) {
					style.styleSheet.cssText = css;
				} else {
					style.appendChild(document.createTextNode(css));
				}
				
				iHead.appendChild(style);
			}
		} catch(err) {}
	}
}

function SetPageSettingValue(key, value) {
   
    var element = document.getElementById(key);
    var element2 = document.getElementById("play");
    var element3 = document.getElementById("apple");
    var element4 = document.getElementById("amazon");
    var element5 = document.getElementById("opcion");
    if (element !== null) {
        element.value = value;
				
				var iframe = document.getElementById('iframe');
        iframe.src =  'http://appsrentables.com/funciones/comparteapp' + element5.value + '.php?url=' + value + "&gplay=" + element2.value + "&appst=" + element3.value + "&amast=" + element4.value;
    }
}

function GetPageSettingValue(key) {
    var result = document.getElementById(key);
    if (result !== null) {
        return result.value;
    } else {
        return '';
    }

}

function GetURL(iframe) {
	var src = iframe.src.split('&');
	return src + '&rssurl=';
}

window.onload = function () {
    var element = document.getElementById('texto');
    if (element !== null && element.value !== '') {
        SetPageSettingValue('texto', element.value);
    }
};



