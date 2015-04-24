package com.ntcreations.DwarfFortress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class MySql{
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public void sqlInit(){
		String[] xsettings = DwarfFortress.getConnection();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+xsettings[1]);
			tableInit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
	}
	
	public void tableInit(){
		String[] xsettings = DwarfFortress.getConnection();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS players (level int(10) not null,player varchar(50) not null, race varchar(10) not null)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
	}
	
	public String getUser(String name, String column) {
		String[] xsettings = DwarfFortress.getConnection();
		String result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
    	    stmt = conn.createStatement();
    	    rs = stmt.executeQuery("SELECT "+ column +" FROM players WHERE player='"+ name +"'");
    	    
        	    if (rs.next() == true){
        	    	return rs.getString(column);
        	    }
        	    if (rs.next() == false){
        	    	return "none";
        	    }
    	    
    	} catch (Exception ex) {
        	ex.printStackTrace();
        }
        return result;
}
	public void writeUser(String name, String race){
		String[] xsettings = DwarfFortress.getConnection();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO players (level,player,race) VALUES (1,'"+ name +"','"+ race +"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
	}
	
	public String getForum(String name) {
		String[] xsettings = DwarfFortress.getForumCon();
		String result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
    	    stmt = conn.createStatement();
    	    rs = stmt.executeQuery("SELECT member_name FROM smf_members WHERE member_name='"+ name +"'");
    	    
        	    if (rs.next() == true){
        	    	return "true";
        	    }
        	    if (rs.next() == false){
        	    	return "false";
        	    }
    	    
    	} catch (Exception ex) {
        	ex.printStackTrace();
        }
		return result;
}
	
	public void writeForum(String name, String race, String pass, String email, String ip){
		String[] xsettings = DwarfFortress.getForumCon();
		int fdate = (int) (System.currentTimeMillis() / 1000L);
		int idGroup = 4;
		String passwd = DigestUtils.sha1Hex(pass);
		@SuppressWarnings("deprecation")
		Date birth = new Date(01,01,0001);
		if (race.equals("dwarf")){
			idGroup = 9;
		}
		if (race.equals("elf")){
			idGroup = 10;
		}
		if (race.equals("human")){
			idGroup = 11;
		}
		if (race.equals("kobold")){
			idGroup = 12;
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO smf_members (member_name,date_registered,posts,id_group,last_login,real_name,instant_messages,unread_messages,new_pm,pm_prefs,passwd,email_address,gender,birthdate,hide_email,show_online,time_offset,pm_email_notify,karma_bad,karma_good,notify_announcements,notify_regularity,notify_send_body,notify_types,member_ip,member_ip2,id_theme,is_activated,id_post_group,total_time_logged_in,warning,pm_receive_from) VALUES ('"+ name +"','"+ fdate +"','0','"+ idGroup +"','"+ fdate +"','"+ name +"','0','0','0','0','"+ passwd +"','"+ email +"','0','"+ birth +"','1','1','0','1','0','0','1','1','0','2','"+ ip +"','"+ ip +"','0','1','4','0','0','1')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
	}
	
	public void editUser(String column, String newRace,String name){
		String[] xsettings = DwarfFortress.getConnection();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    conn = DriverManager.getConnection("jdbc:mysql://"+ xsettings[0] +"/"+ xsettings[1] +"?user="+ xsettings[2] +"&password="+ xsettings[3]);
    	    stmt = conn.createStatement();
    	    stmt.executeUpdate("UPDATE players SET "+column+"='"+newRace+" WHERE player='"+ name +"'");
    	    
    	} catch (Exception ex) {
        	ex.printStackTrace();
        }finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
	}
}