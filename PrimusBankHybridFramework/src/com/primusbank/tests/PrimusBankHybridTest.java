package com.primusbank.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.primusbank.constants.PrimusBankConstants;
import com.primusbank.library.AdminHomePage;
import com.primusbank.library.BankerHomePage;
import com.primusbank.library.CustomerHomePage;
import com.primusbank.library.PrimusBankHomePage;
import com.utils.XLUtils;

public class PrimusBankHybridTest extends PrimusBankConstants 
{
	XLUtils xl=new XLUtils();
	String xlfile="D:\\Frameworks1130\\PrimusBankHybridTest\\src\\com\\primusbank\\keywords\\PrimusBankKeywords.xlsx";
	String tcsheet="TestCases";
	String tssheet="TestSteps";
	
	PrimusBankHomePage phome=new PrimusBankHomePage();
	AdminHomePage ahome=new AdminHomePage();
	BankerHomePage bhome=new BankerHomePage();
	CustomerHomePage chome=new CustomerHomePage();
	
	@Test
	public void keywordDrivenTest() throws IOException
	{
		int tccount,tscount;
		String tcexeflg,tcid,tstcid,keyword;
		boolean res=false;
		String tcres="";
		
		tccount=xl.getRowCount(xlfile, tcsheet);
		tscount=xl.getRowCount(xlfile, tssheet);
		
		for (int i = 1; i <= tccount; i++) 
		{
			tcexeflg=xl.getCellData(xlfile, tcsheet, i, 2);
			if (tcexeflg.equalsIgnoreCase("Y")) 
			{
				tcid=xl.getCellData(xlfile, tcsheet, i, 0);
				for (int j = 1; j <=tscount ; j++) 
				{
					tstcid=xl.getCellData(xlfile, tssheet, j, 0);
					if (tcid.equalsIgnoreCase(tstcid)) 
					{
						keyword=xl.getCellData(xlfile, tssheet, j, 4);
						switch (keyword.toUpperCase()) 
						{
						case "ADMINLOGIN":
							phome.adminuid=xl.getCellData(xlfile, tssheet, j, 5);
							phome.adminpwd=xl.getCellData(xlfile, tssheet, j, 6);
							res=phome.adminLogin(phome.adminuid, phome.adminpwd);
							break;
						case "ADMINLOGOUT":
							res=ahome.adminLogout();						
							break;
						case "BANKERLOGIN":
							phome.branchname=xl.getCellData(xlfile, tssheet, j, 5);
							phome.bankeruid=xl.getCellData(xlfile, tssheet, j, 6);
							phome.bankerpwd=xl.getCellData(xlfile, tssheet, j, 7);
							res=phome.bankerLogin(phome.branchname,phome.bankeruid,phome.bankerpwd);
							break;
						case "BANKERLOGOUT":
							res=bhome.bankerLogout();	
							break;
						case "CUSTOMERLOGIN":
							phome.custuid=xl.getCellData(xlfile, tssheet, j, 5);
							phome.custpwd=xl.getCellData(xlfile, tssheet, j, 6);
							res=phome.customerLogin(phome.custuid, phome.custpwd);
							break;
						case "CUSTOMERLOGOUT":
							res=chome.customerLogout();
							break;
						}
						String tsres=null;
						if (res) 
						{
							tsres="Pass";
							xl.setCellData(xlfile, tssheet, j, 3, tsres);
							xl.fillGreenColor(xlfile, tssheet, j, 3);
						} else 
						{
							tsres="Fail";							
							xl.setCellData(xlfile, tssheet, j, 3, tsres);
							xl.fillRedColor(xlfile, tssheet, j, 3);
						}
						if (!tcres.equalsIgnoreCase("FAIL")) 
						{
							tcres=tsres;
						}
					}
				}
				xl.setCellData(xlfile, tcsheet, i, 3, tcres);
				if (tcres.equalsIgnoreCase("PASS")) 
				{
					xl.fillGreenColor(xlfile, tcsheet, i, 3);
				} else 
				{
					xl.fillRedColor(xlfile, tcsheet, i, 3);	
				}
				
			} else 
			{
				xl.setCellData(xlfile, tcsheet, i, 3, "Blocked");
				xl.fillRedColor(xlfile, tcsheet, i, 3);
			}
		}
		
	}
	
	
	
}
