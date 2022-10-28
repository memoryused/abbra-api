<%@page import="com.sit.abbra.abbraapi.util.EExtensionApiUtil"%>
<%@page import="java.io.IOException"%>
<%@page import="com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig"%>
<%@page import="com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest"%>
<%@page import="com.sit.abbra.abbraapi.enums.LoginCaptcha"%>
<%@page import="com.sit.abbra.abbraapi.core.config.parameter.domain.ConfigSystem"%>
<%@page import="com.sit.abbra.abbraapi.common.EExtensionApiCommonWS"%>
<%@page import="com.sit.abbra.abbraapi.core.security.login.service.LoginManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.List"%>
<%@page import="util.string.StringUtil"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.apache.logging.log4j.Logger"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>E-EXTENSION API Login</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
<script src="webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="webjars/bootstrap/4.5.3/js/bootstrap.min.js"></script>
<link href="webjars/bootstrap/4.5.3/css/bootstrap.min.css" rel="stylesheet"/>
<link href="css/bootstrap-icons-1.3.0/font/bootstrap-icons.css" rel="stylesheet"/>
<link href="css/style.css" rel="stylesheet">
<link href="css/font/PSLKanda-font.css" rel="stylesheet"/>
<%
response.addHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.addHeader("Cache-Control", "pre-check=0, post-check=0");
	response.setDateHeader("Expires", 0);

	boolean isInternal = false;
	boolean isInternalBackup = false;
	boolean isExternal = false;
	boolean showCaptcha = false;
	
	Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	LoginManager manager = new LoginManager(logger);
	
	String host = request.getHeader("Host");
	
	AuthorizeRequest authorizeRequest = manager.getAuthorizeByKey(request.getParameter("id"));
	if(authorizeRequest != null){
		isInternal = true;
	} else {
		String sendRedirectURL = EExtensionApiUtil.getRedirectLoginURLFromHostURL(host);
		if (sendRedirectURL.isEmpty()) {
	redirectErrorPage(request, response, logger);
		} else {
	response.sendRedirect(sendRedirectURL);
		}
	}
	
	String msgDesc = (String) request.getAttribute("msgError");
	String msgType = (String) request.getAttribute("msgType");
	
	ResourceBundle labels = EExtensionApiUtil.getBundleMessageAlert(ParameterConfig.getApplication().getApplicationLocale());
	String msg = EExtensionApiUtil.getMessage(labels, "10002");
	String msgCaptcha = EExtensionApiUtil.getMessage(labels, "10011");
	
	ResourceBundle secBundle = EExtensionApiUtil.getBundleMessageSecurity(ParameterConfig.getApplication().getApplicationLocale());
	String secLoginLabel = EExtensionApiUtil.getMessage(secBundle, "sec.login");
	String secUsernamePlaceholder = EExtensionApiUtil.getMessage(secBundle, "sec.usernamePlaceholder");
	String secPasswordPlaceholder = EExtensionApiUtil.getMessage(secBundle, "sec.passwordPlaceholder");
	String secSystemName = EExtensionApiUtil.getMessage(secBundle, "sec.systemName");
	String recommandation = EExtensionApiUtil.getMessage(secBundle, "sec.footer_rec");
	String linkDownload = EExtensionApiUtil.getMessage(secBundle, "sec.download_firefox"); 
	String linkDownloadPrinter = EExtensionApiUtil.getMessage(secBundle, "sec.download_sw_printer"); 
	String loginFooter = "Copyright © 2022 Project. All rights reserved. \r\nProject version 2022.08.00 build 413";
%>
<%!private void redirectErrorPage(HttpServletRequest request, HttpServletResponse response, Logger logger){
	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	response.setStatus(404);
	RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.html");
    try {
        dispatcher.forward(request, response);
    } catch (Exception e) {            
    	logger.error("Login.jsp error: ", e);
    }
}%> 
<style type="text/css">
	.icon-eyes {
		background-color: white;
		cursor: pointer; 
		right: 70px;
		margin-top: 6px;
		font-size: 20px; 
		color: rgb(9, 50, 87);
	   	position: absolute;
	}
	
	.icon-reload {
		cursor: pointer; 
		right: 70px;
		margin-top: 6px;
		font-size: 20px; 
		color: #000000;
	   	position: absolute;
	   	
	}
	
	.icon-left {
		background-color: transparent;
		font-size: 25px;
		color: #000000;
		min-width: 50px;
 		height: 46px;
 		border-right: none!important;
 		width: 100px;
 		padding-left: 15px;
	}
	
	.input-field {
  		height: 46px!important;
  		background: transparent;
  		border-top: none;
  		border-bottom: none;
  		border-right: none;
  		border-top-right-radius: 20px!important;
  		border-bottom-right-radius: 20px!important; 
  		margin-left: -29px;
	}
	
	.logo {
        top: 20px;
        right: 20px;
        width: 150px;
        /*background:  #ffcc07;*/
        margin-bottom: 20px;
        text-align: center;
    }
    
    .contact {
		position: fixed;
		left: 0;
		bottom: 0;
		width: 100%;
		text-align: center;
		color: #676767;
    }
    
    .contact p{
    	margin-bottom: 0px;
    }
	
	input:-webkit-autofill,
	input:-webkit-autofill:hover, 
	input:-webkit-autofill:focus, 
	input:-webkit-autofill:active
	{
	 -webkit-box-shadow: 0 0 0 30px white inset !important;
	}
	
	::-webkit-credentials-auto-fill-button {
	   visibility: hidden;
	}
	
	input { filter: none; }
	
    ::-ms-reveal,
	::-ms-clear { display:none; }
	
	.header {
    	text-align: center;
    }
    
    @media (min-width: 1200px) {
	    .container{
	        max-width: 800px;
	    }
	}
	
	.input-container {
	  display: flex;
	  width: 100%;
	  margin-bottom: 15px;
	  background: white;
	  border-radius: 20px;
	}
	
	.version {
		text-align: center;
		font-size: 11px;
		margin: 25px 0px 0px 0px;
	}
	
	img {
		width: 100%;
		min-height: 50px;
	}
	
	.sysName {
		 font-size : 1.5rem;
	}
	
	.btn-login {
		border-radius: 20px!important;
		height: 40px;
		width: 100px !important;
		background-color: #ffcb08 !important;
		border-color: #ffcb08 !important;
		color: #3d1a6e !important;
		font-weight: bold;
	}
	
	.show-alert {
		height: auto;
   		min-height: 46px;
		width: 100%;
		margin-bottom: 1.5rem!important;
	}
	
	.btn-block {
		width: 40% !important;
		margin: auto;
	}
	
	/* 2022-09-21 [2022EXTVFS-58] sitthinarong.j */
	.custom-link-hover:hover {
		color: yellow;
	}
}
</style>
<script type="text/javascript">
	
	function sf(){
		document.getElementById("isOverride").value = false;
		var msgType = "<%= msgType %>";
		if( msgType == "C"){
			// แสดง popup override
			jQuery('#confirmOverride').modal('show');
			
			// event กดปุ่ม ok ของ popup override
			jQuery("#btnConfimOverride").on("click", function(){
				jQuery('#confirmOverride').modal('hide');
				document.getElementById("isOverride").value = true;
				document.getElementById("form").submit();
			});
			
			// event กดปุ่ม cancel ของ popup override
			jQuery("#cancelConfimOverride").on("click", function(){
				jQuery('#confirmOverride').modal('hide');
				document.getElementById("user").focus();
			});
		}
		
		const togglePassword = document.querySelector('#togglePassword');
		const password = document.querySelector('#pass');
		
		togglePassword.addEventListener('click', function (e) {
		    // toggle the type attribute
		    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
		    password.setAttribute('type', type);
		    // toggle the eye / eye slash icon
		    this.classList.toggle('bi-eye');
		});
	}
	
	function login(){
		if(document.getElementById("user").value == ''){
			showMsg('<%=labels.getString("10002")%>');
			document.getElementById("userInput").classList.add("requiredInput");
			document.getElementById("user").focus();
			return false;
		} else {
			document.getElementById("userInput").classList.remove("requiredInput");
		}
		
		
		if(document.getElementById("pass").value == ''){
			showMsg('<%=labels.getString("10002")%>');
			document.getElementById("passInput").classList.add("requiredInput");
			document.getElementById("pass").focus();
			return false;
		} else {
			document.getElementById("passInput").classList.remove("requiredInput");
		} 
		
		document.getElementById("form").submit();
	}

	// แสดง msg ที่หน้าจอ
	function showMsg(msg){
		// กรณีไม่เคยมีการแสดง msg
		if(document.getElementsByClassName("show-alert")[0].innerHTML.trim() == ""){
			document.getElementsByClassName("show-alert")[0].innerHTML += "<div class='alert alert-danger margin-field' role='alert'>"+msg+"</div>";
		} else { // กรณีมีการแสดง msg ค้างอยู่
			document.getElementsByClassName("show-alert")[0].innerHTML = "<div class='alert alert-danger margin-field' role='alert'>"+msg+"</div>";
		}
	}
	
	function downloadBrowser(){
		alert();
	}
</script>
</head>
<body onload="sf();">
	<div class="container-fluid">
		<div class="row no-gutter">
			<!-- The content half -->
			<div class="col-12 justify-content-center">
				<div class="card-12">
					<div class="login d-flex align-items-center py-5">

						<!-- Demo content-->
						<div class="container">
							<div class="row">
								<div class="col-sm-9 col-md-7 col-lg-5 col-xl-6 mx-auto login-bg">
									<div class="header">
										<img src="css/logo/logo.png" alt="logo" class="logo"/>
										<h4 class="sysName"><%=secSystemName%></h4>
									</div>
									<div class="show-alert">
									<% 
		                            	if(msgDesc != null && msgType != "C"){
		                            		%>
											<div class="alert alert-danger margin-field" role="alert">
												<%=msgDesc%>
											</div>
											<%
		                            	}
		                            %>
		                            </div>
									<form id="form" method="post" action="signup" autocomplete="off">
										<div id="userInput" class="form-group mb-4 input-container">
											<span class="bi bi-person icon-left" id="userIcon">
											</span>
											<input id="user" name="username" type="text"
												placeholder="<%=secUsernamePlaceholder%>"
												autofocus="autofocus"
												maxlength="50"
												class="form-control shadow-sm px-4 input-field">
										</div>
										<div id="passInput" class="form-group mb-4 input-container">
											<span class="bi bi-lock icon-left" id="passIcon"></span>
											<span class="bi bi-eye-slash icon-eyes" id="togglePassword">
											</span>
											<input id="pass" name="password" type="password"
												placeholder="<%=secPasswordPlaceholder%>"
												maxlength=100
												class="form-control shadow-sm px-4 input-field">
										</div>
										
										<input type="hidden" name="authId" value="<%=request.getParameter("id")%>" autocomplete="off">
										<button type="submit" class="btn btn-warning btn-block mb-2 rounded-pill shadow-sm btn-login" onclick="return login();"><%=secLoginLabel%></button>
										<input type="hidden" id="isOverride" name="isOverride">
									</form>
									<p class="version"><%=loginFooter%></p>  
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="confirmOverride" data-keyboard="false" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="confirmOverride" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Comfirmation</h5>
	      </div>
	      <div class="modal-body">
	        <%=msgDesc %>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-modal" id="btnConfimOverride">Ok</button>
	        <button type="button" class="btn btn-light btn-modal btn-modal-cancel" id="cancelConfimOverride">Cancel</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>