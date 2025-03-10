package net.codejava.Classes;

import java.util.*;

public class User_Mitigation {
	String Username;
	String Mail;
	Boolean Mitigation;
	Date MitigationDate;
	String MitigationType;
	String Remarks;
	
	public void setUsername(String s)
	{
		this.Username=s;
	}
	
	public void setMail(String s)
	{
		this.Mail=s;
	}
	
	public void setMitigation(Boolean s)
	{
		this.Mitigation=s;
	}
	
	public void setMitigationDate(Date s)
	{
		this.MitigationDate=s;
	}
	
	public void setMitigationType(String s)
	{
		this.MitigationType=s;
	}
	
	public void setRemarks(String s)
	{
		this.Remarks=s;
	}
	
	public boolean updateSecurity_Risk()
	{
		return true;
	}
	
	public boolean DisplayDashboard()
	{
		return true;
	}
}
