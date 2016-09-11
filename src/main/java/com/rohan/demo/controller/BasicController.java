package com.rohan.demo.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class BasicController {

	@RequestMapping(value = "/Test", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		model.addAttribute("msg", "Maven Java Web Application Project: Success!");

		return "cp1";
	}

	@RequestMapping(value = "/Print/{arg}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String arg, ModelMap model) {
		model.addAttribute("msg", "Maven Java Web Application Project, input variable: " + arg);

		return "cp1";
	}

	@RequestMapping(value = "/db")
	public String connectDB(Model m) {

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			
			// ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory)
			// initContext.lookup("java:comp/env/jms/jmsFactory");
			// //javax.jms.Connection connection =
			// jmsConnFact.createConnection();
			// System.out.println("Is jms connection null --->" + (factory == null));

			DataSource ds = (DataSource) envContext.lookup("jdbc/MyDB");
			Connection conn = ds.getConnection();
			Statement statement = conn.createStatement();
			String sql = "select EMPID, NAME from EMPLOYEE";
			ResultSet rs = statement.executeQuery(sql);

			String message = "";
			while (rs.next()) {
				message += rs.getInt("EMPID") + "&nbsp; &nbsp; &nbsp; &nbsp;" + rs.getString("NAME") + "<br>";
			}
			m.addAttribute("msg", message);
		} catch (NamingException ex) {
			System.err.println(ex);
		} catch (SQLException ex) {
			System.err.println(ex);
		}

		// Method [] meth =
		// com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource.class.getMethods();
		//
		// for(Method mh : meth)
		// System.out.println(mh.getName());

		// org.apache.activemq.jndi.ActiveMQInitialContextFactory fact = ;
		// fact.getClass().getMethods();

		// Method[] meth = javax.jms.Topic.class.getMethods();
		// for (Method mh : meth)
		// System.out.println(mh.getName());

		// Method[] meth =
		// org.apache.activemq.ActiveMQConnectionFactory.class.getMethods();
		// for (Method mh : meth)
		// System.out.println(mh.getName());
		
		
		
		return "cp2";
	}

}