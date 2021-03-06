<%@page import="com.java2.pojo.User"%><%-- 
<%@page import="com.sun.java.swing.plaf.windows.resources.windows"%> --%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/nicejforms.js"></script>
<script type="text/javascript" src="js/thickbox.js"></script>
<script type="text/javascript" src="js/audioplayer.js"></script>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link href="css/page.css" rel="stylesheet" type="text/css" />
<link href="css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/LoadingStatus.css" type="text/css" />
<style type="text/css" media="screen">
@import url(css/niceforms.css);
</style>
<script type="text/javascript">
		$(document).ready(function(){
			$.NiceJForms.build();
			dopage('index_ajax.jsp?page=1');
		});
		
		function dopage(ajaxurl){
			$('#LoadingStatus').show();
			$.ajax({url: ajaxurl,
			type: 'GET',
			dataType: 'html',
			timeout: 30000,
			async : false,
			error: function(){$('#content').html('<table  width="50%" border="0" align="center"> <tr> <td class="center_article" align="center">获取文章失败，请刷新此页面！！！</td></tr></table>');$('#LoadingStatus').hide(500);
			},
			success: function(html){
				//window.location="#article_md";
				$('#LoadingStatus').hide(1000);
				$('#content').html(html);
			}
			});
		}
		
		function addbox(music_id){
			$.ajax({url: 'addtobox.action?music_id=' + music_id,
			type: 'GET',
			dataType: 'html',
			timeout: 30000,
			async : false,
			error: function(){
				alert("出现错误！");
			},
			success: function(html){
				//window.location="#article_md";
				alert(html);
			}
			});
		}
		</script>
</head>
<%-- <%String error = (String)session.getAttribute("Error");
  	if(error!=null){
  		request.getRequestDispatcher("register.jsp?height=270&width=300&modal=true").forward(request, response);
  	}else{
  		error = "";
  	}
   %> --%>
<%User newUser = (User)request.getAttribute("new_user");
   String name = "";
   String pwd = "";
   if(newUser!=null){
  		name = newUser.getAccount();
   		pwd = newUser.getPassword();
  	}
  %>
  <%String error = (String)request.getAttribute("Error");
  	if(error==null){
  		error = "";
  	}
   %>
<body>
	<div id="header">
		<div id="logo">
			<h1>JerryMusic</h1>
			<h2></h2>
		</div>
		<div id="menu">
			<ul>
				<li class="active"><a href="index.jsp" accesskey="1" title="">首页</a>
				</li>
				<li><a href="Controller?op=QueryAllMusic" accesskey="2"
					title="">音乐盒</a></li>
				<li><a href="show.jsp" accesskey="3" title="">音乐播放</a></li>
				<li><a href="uploadmusic.jsp" accesskey="3" title="">分享歌曲</a></li>
				<li><a href="together.jsp" accesskey="3" title="">音乐心情</a></li>
			</ul>
		</div>
	</div>
	<div id="LoadingStatus">
		<img src="images/ajax-loader.gif" />
	</div>
	<hr />
	<div id="page">

		<div id="bg">
			<a name="article_md"><img src="images/untitled.png" width="680" /></a>


			<!-- end contentn -->
			<div id="sidebar">
				<div id="about-box" style="font-size: 12px">
					<%
								User user = (User)session.getAttribute("User");
								if (user == null) {
							%>
					<div>
						<form action="Controller?op=login" method="post">
							<label for="textinput"> &nbsp;&nbsp;用户名： </label> <br />
							&nbsp;&nbsp; <input type="text" id="textinput" name="userName"
								style="width:170;" size="15" maxlength="16" value="<%=name %>" />
							<br /> <label for="passwordinput"> &nbsp;&nbsp;密 码： </label> <br />
							&nbsp;&nbsp; <input type="password" id="passwordinput"
								name="userPwd" style="width:170;" size="15" maxlength="16"
								value="<%=pwd %>" /> <br /> <br /> &nbsp;&nbsp;&nbsp;&nbsp; <a
								href="register.jsp?height=175&width=300&modal=true"
								class="thickbox" title="我要注册">我要注册</a> &nbsp;&nbsp; <input
								type="submit" value="登  陆" />

						<p align="center"><%=error %></p>
						</form>
					</div>


					<p></p>
					<%
							} else {
						%>
					<p>
						<%=user.getNickname()%>，欢迎您回来！
					</p>
					<p>
						如果您有音乐分享，您可以点我进行 <a href="uploadmusic.jsp" style="color: red">[上传音乐]</a>！
						<br />
					</p>
					<p>
						退出请点 <a href="Controller?op=logout" style="color: #FF0000">[注销登陆]</a>！
					</p>
					<%
							}
						%>
				</div>
				<ul>
					<li>
						<h2>最新消息</h2>
					</li>
					<li>
						<h2>友情链接</h2>
					</li>
				</ul>
			</div>
			<!-- end sidebar -->
			<div style="clear: both;">&nbsp;</div>
		</div>
	</div>
	<!-- end page -->
	<hr />
	<div id="footer">
		<p>(c) 2018 JerryMusic</p>
	</div>
</body>
</html>
