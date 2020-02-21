package com.java2.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import net.sf.json.JSONArray;

import com.java2.pojo.Music;
import com.java2.pojo.User;
import com.java2.service.UserService;
import com.java2.serviceImpl.UserServiceImpl;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

public class Controller extends HttpServlet {

	UserService service = new UserServiceImpl();
	Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String operation = request.getParameter("op");
		try {
			if ("login".equals(operation)) {
				this.login(request, response);
			}
			if ("QueryAllMusic".equals(operation)) {
				this.QueryAllMusic(request, response);
			}
			if ("register".equals(operation)) {
				this.Register(request, response);
			}
			if("shwoMusic".equals(operation)){
				this.ShowMusic(request,response);
			} 
			if("logout".equals(operation)){
				this.Louout(request,response);
			}
			if("upload".equals(operation)){
				this.Upload(request,response);
			}
			if("liuyan".equals(operation)){
				this.Lunyan(request,response);
			}
			if("insert".equals(operation)){
				this.Insert(request,response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void Insert(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String url = request.getParameter("title");
		String singer = request.getParameter("singer");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		char[] ch = url.toCharArray();
		String title = "";
		for (int i = 0; i < ch.length; i++) {
			if(String.valueOf(ch[i]).equals(".")){
				break;
			}else{
				title += String.valueOf(ch[i]);
			}
		}
		User user = (User) request.getSession().getAttribute("User");
		int userid = user.getId();
		int num = service.Insert(title,singer,time,url,userid);
		if(num!=0){
			request.getRequestDispatcher("Controller?op=QueryAllMusic").forward(request,
					response);
		}
	}

	private void Lunyan(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String nickName=request.getParameter("name");
		String msg = request.getParameter("comments");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(new Date());
		String str = nickName+"留言："+msg+"    "+time;
		ArrayList<String> msgList = (ArrayList<String>)request.getSession().getAttribute("msgList");
		 
		if(msgList==null){
			msgList = new ArrayList<String>();
		}
		msgList.add(str);
		/*JSONArray array=JSONArray.fromObject(msgList);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(array.toString());*/
		//sout.flush();
		//sout.close();
				
		request.getSession().setAttribute("msgList", msgList);
		request.getRequestDispatcher("show.jsp").forward(request,response);
	}
	private final String SAVEPATH="D:/myeclipsepojo/JerryMusic/WebRoot/music/";
	private void Upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("User");
		if (user == null) {
			request.setAttribute("Msg", "请登录后再进行上传");
			request.getRequestDispatcher("uploadmusic.jsp").forward(request,
					response);
		} else {
			SmartUpload smartUpload = new SmartUpload();
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
				try {
					com.jspsmart.upload.File files = smartUpload.getFiles().getFile(0);
					String fileName = files.getFileName();
					files.saveAs(SAVEPATH + fileName);
					request.setAttribute("fileName", fileName);
					request.getRequestDispatcher("upload.jsp").forward(request,
							response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private void Louout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		request.getSession().removeAttribute("User");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	private void ShowMusic(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String url = request.getParameter("url");
		String singer = request.getParameter("singer");
		if(url==null||singer==null){
			url = (String)request.getSession().getAttribute("musicUrl");
			url = new String(url.getBytes("iso-8859-1"),"utf-8");
			singer = (String)request.getSession().getAttribute("singer");
		}else {
			url = new String(url.getBytes("iso-8859-1"),"utf-8");
			singer = new String(singer.getBytes("iso-8859-1"),"utf-8");
		}
		request.getSession().setAttribute("musicUrl", url);
		request.getSession().setAttribute("singer", singer);
		request.getRequestDispatcher("show.jsp").forward(request, response);
	}

	private void Register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String name = request.getParameter("userName");
		String pwd = request.getParameter("userPwd");
		String repwd = request.getParameter("confirmPwd");
		String sex = request.getParameter("sex");
		String nick = request.getParameter("nickName");
		if (name == null || name == "" || pwd == null || pwd == ""
				|| repwd == null || repwd == "" || sex == null || sex == ""
				|| nick == null || nick == "") {
			request.setAttribute("Error", "需要输入的不可为空！");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else {
			if (pwd.equals(repwd)) {
				User user = new User();
				user.setAccount(name);
				user.setPassword(pwd);
				user.setMusic_box("空");
				user.setSex(sex);
				user.setNickname(nick);
				User one_user = service.Register(user);
				if (one_user != null) {
					request.setAttribute("new_user", one_user);
					request.getRequestDispatcher("index.jsp").forward(request,
							response);
				}else{
					request.setAttribute("Error", "注册失败！");
					request.getRequestDispatcher("index.jsp")
							.forward(request, response);
				}
			}else {
				request.setAttribute("Error", "两次输入的密码不一致！");
				request.getRequestDispatcher("index.jsp")
						.forward(request, response);
			}
		}
	}

	private void QueryAllMusic(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		User user = (User)request.getSession().getAttribute("User");
		ArrayList<Music> musicArr = null;
		if(user==null){
			User user1 = new User();
			user1.setId(0);
			musicArr = service.QueryAllMusic(user1);
		}else{
			musicArr = service.QueryAllMusic(user);
		}
		request.getSession().setAttribute("AllMusic", musicArr);
		request.getRequestDispatcher("musicbox.jsp").forward(request,
					response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String account = request.getParameter("userName");
		String password = request.getParameter("userPwd");
		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		User user1 = service.login(user);
		if (user1 != null) {
			request.getSession().setAttribute("User", user1);
			request.getRequestDispatcher("Controller?op=QueryAllMusic").forward(request,
					response);
		} else {
			request.setAttribute("loginErrorMsg", "账号或密码不正确！");
			request.getRequestDispatcher("index.jsp").forward(request,
						response);
		}
	}

}
