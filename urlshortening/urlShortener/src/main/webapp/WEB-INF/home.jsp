<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
     <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HOME</title>

<!-- <link rel="stylesheet" type="text/css" href="/jsp/semantic.min.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="/jsp/style.css"> -->
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.6/semantic.min.css">
<!-- <script src="/jsp/semantic.min.js"></script> -->
<!-- <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.1.6/semantic.min.js"/></script>
<script src="http://demos.krizna.com/1326/jquery.zclip.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$( ".infoToggle" ).click(function() {
	  $( ".infoWrapper" ).toggle();
	});
	
	$( ".cbtn" ).click(function() {
	  $( ".ctext" ).toggle('fast');
	  $( ".cbtn" ).toggle('fast');
	  $( ".close" ).toggle('fast');
	});
	
	$( ".close" ).click(function() {
	  $( ".ctext" ).toggle('fast');
	  $( ".cbtn" ).toggle('fast');
	  $( ".close" ).toggle('fast');
	});
	
});
$(function() {
	$("#btnclick").click(function() {
		var url = '';
		url = 'http://' + $('#q').val();
		//$(location).attr('href', url);
		var win = window.open(url, '_blank');
        win.focus();
		//window.open = $(location).attr('href', url);
	})
	$(".test-link").click(function(){
		var url = '';
		url = 'http://' + $(this).attr('url');
		var win = window.open(url, '_blank');
		win.focus();
	})
});
</script>


<style type="text/css">
body { background-color: #F4F4F4; margin:0px;}

.alignL{ text-align:left;}
.alignR{ text-align:right;}
.clear{ clear:both;}

.header-container{background-attachment:scroll;background:#DDD;margin:0 auto 20px auto; padding-bottom:0;padding-left:18px;padding-right:0;padding-top:0;}
.header{background-attachment:scroll; padding:0 18px 11px 0;overflow:hidden;}
.header-right{color:#4C4C4C;float:right;padding-bottom:0;padding-left:0;padding-right:20px;margin-top:-30px;position:relative;z-index:2; font-size:20px;}
.header-top{overflow:hidden;}
.logo{margin:2px 4px; width:150px; float:left; margin-top:10px;}


.mainWrapper{ width:1000px; margin:15px auto;}

.urlWrapper{ text-align:left; margin:30px 0px;}

.infoToggle{ cursor:pointer; display:inline-block; color:rgba(10,153,223,1.00); text-decoration:underline;}
.infoWrapper{ border:solid 1px #EEE; padding:20px; background:#F9FAFB; text-align:left;}

.ctext{width:350px;}

ul#menu li {
    display:inline;
}

ul.square {
    list-style-type: square;
}

.custom-file-input {
  padding: 10px 15px;
    float: left;
	outline:none;
}
.custom-file-input::-webkit-file-upload-button {
  visibility: hidden;
}
.custom-file-input::before {
  content: 'Upload file';
  background-color: #FBBD08;
    color: #FFF;
    text-shadow: none;
    background-image: none;
  white-space: nowrap;
  -webkit-user-select: none;
  cursor: pointer;
  font-weight: 700;
  font-size: 10pt;
  padding: 10px 15px;
  border-radius: .28571429rem;
  margin-left:-15px;
}
.custom-file-input:hover::before {
  border-color: black;
}
.custom-file-input:active {
  outline: 0;
}
.custom-file-input:active::before {
  background: -webkit-linear-gradient(top, #e3e3e3, #f9f9f9); 
}

body {
  padding: 20px;
}</style>

<script>
  $(document)
    .ready(function() {
      $('.urlWrapper')
        .form({
          fields: {
            url: {
              identifier  : 'url',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter a valid Url'
                }
              ]
            },	
            customUrl: {
              identifier  : 'customUrl',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your custom url'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script>


</head>
<center>
<body>

<div class="header-container" style="height:60px;">
    <div class="header">
        <div class="header-top">
        	<a href="#" title="India's largest daily deals site">
            	<img class="logo" src='http://i2.sdlcdn.com/img/storeFrontFeaturedProductAdmin/10/150x45.png' alt="India's largest daily deals site">
            </a>
            <div style="font-size:30px; line-height:57px;">URLShortening service</div>
            <div class="header-right">
                <a href="/urlshortener/logout">Log Out</a>
            </div>
        </div>
    </div>
</div>



<div class="mainWrapper">
<!--<h4 class="ui dividing header alignL">${message}</h4>-->
<div class="topcorner"><a href="/urlshortener/display">Back to Home</a></div>

<form action="/urlshortener/addurl" method="POST" class="form">

<div class="urlWrapper">
    <div class="ui labeled input" style="width:400px; margin-right:10px;">
      <input type="text" name = "url" placeholder = "Enter URL:" />
    </div>
    <div class="ctext ui labeled input" style="margin-right:10px; display:none;">
      <input type="text" name = "customUrl" placeholder = "Custom URL:" />
      
    </div>
    <input type="button" value="Hide" class="close ui yellow button" style="display:none;"/>
    <input type="button" value="Custom" class="cbtn ui yellow button"/>
    <input type="submit" value="GENERATE" class="ui teal button"/>
    <div>${message}</div>
</div>



</form>
<div class="alignL">
    <form action="/urlshortener/file" method="post" enctype="multipart/form-data">
        <input type="file" class="custom-file-input" name="filedata" size="50" accept=".csv,.xls,.xlsx,.xlsm,.xlsb," />
        <input type="submit" value="Submit" class="ui teal button">
    </form>
</div>

<c:if test="${enableTestLink}">
<div class="urlWrapper">
	<div class="ui labeled input" style="width:500px; margin-right:10px;">
      <input type="text" name = "url" placeholder = "sdit/example.com" id="q" value="${generatedUrl }" readonly/>
      
    </div>
    <input type="submit" value="Test Link" class="ui teal button" id="btnclick"/>
    <!-- <br><a href="#" class="copy" id="ctext"><span id="xtext">Click to copy link </span></a> -->
</div>
</c:if>

<div class="clear"></div>
<div>
	<h3 class="infoToggle">How it works ?</h3>
    <div class="infoWrapper" style="display:none;">
    	<ol>
    		<li>User can create either generic or custom shortened URL's</li>
    		<li>One variable name can be used only once to create custom shortened URL's</li>
    		<li>Custom variable name can accept a combination of digits, alphabets, hyphen (-) and Underscore (_) .</li>
    		<li>One URL can be mapped to multiple shortened URL's but the same is not true for shortened URL's</li>
    		<li>Custom slug cannot be greater than 14 characters </li>
    		<li>User can upload a file with URL's to generate multiple shortened URL's. The accepted file format is csv.</li>
    		<li>The uploaded should have the following columns names to be accepted as the correct file:
    			<ul>
    				<li>First Column: url</li>
    				<li>Second Column: shortenedurl</li>
    			</ul>
    		</li>
    	</ol>
    </div>	
</div>


<table class="ui celled striped table">
	<thead>
        <tr>
            <th>Url</th>
            <th>ShorteneduUrl</th>
            <th>Link Created By:</th>
            <th>HitCount</th>
            <th>Test Link</th>
        </tr>
	</thead>

<c:forEach items="${urlList}" var="entryiterator" varStatus="status">

<tr>
	<td><c:out value="${entryiterator.url}" /></td>
	<td><c:out value="${entryiterator.shortenedUrl}" /></td>
	<td><c:out value="${entryiterator.userID}" /></td>
	<td><c:out value="${urlHitList[status.index] }" /></td>
	<td><input type="submit" value="Test Link" class="ui teal button test-link"  url="${entryiterator.shortenedUrl }"/></td>
</tr>
</c:forEach>
</table>




<%-- <table border="5" bordercolor="blue">
<c:forEach items="${entries}" var="entryiterator">

<tr>
	<td>${entryiterator.url}</td>
	<td>${entryiterator.shortenedUrl}</td>
</tr>


</c:forEach>
</table> --%>


<!-- <div class="alignR">
    <form action="/urlshortener/display" method="get">
        <input type="submit" value="Display list" class="positive ui button">
    </form>
</div> -->

</div>

<%-- <ul id="menu" class="square">
<c:forEach items="${paginationList}" var="paginationIterator" >
	<li><a href="/urlshortener/display?page=${paginationIterator }"/>${paginationIterator }</a></li>
</c:forEach>
</ul> --%>

<div class="ui pagination menu">
      <%-- <c:forEach items="${paginationList}" var="paginationIterator" >
	<li><a href="/urlshortener/display?page=${paginationIterator }"/>${paginationIterator }</a></li>
</c:forEach> --%>
      
      <c:forEach items="${paginationList}" var="paginationIterator" >
      		<a class="item" href="/urlshortener/display?page=${paginationIterator }">${paginationIterator }</a>
      </c:forEach>
    </div><br><br>
</center>

<script>
$( "a.copy" ).each(function() { // identifying copy links using class "copy"
	var id=$(this).attr("id"); 
	$('a#ctext').zclip({
     	copy:function(){return $('#q').val();},
	afterCopy:function(){
		$( "span" ).each(function(){ 
			var val=$(this).attr("id");
			$(this).text("Copy "+val);  // changing all span text to original
		});
		 $('#xtext').text("Copied"); // changing particular span text
	}
    });
});
</script>

</body>
</html>
