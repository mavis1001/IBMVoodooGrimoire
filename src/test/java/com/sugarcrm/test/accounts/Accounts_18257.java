package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;

public class Accounts_18257 extends SugarTest {
	AccountRecord myAccount;
	VoodooControl dataFldToggle;
	DataSource columns;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
	}

	/**
	 * Hide columns on list view layout
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		columns = testData.get(testName);
		
		sugar.accounts.navToListView();
		//TODO VOOD-467 need replace these controls after lib file is done
		VoodooControl fldToggle= new VoodooControl ("button", "xpath", "//table[@class='table table-striped dataTable']//button[@data-action='fields-toggle']");
		fldToggle.click();
		VoodooControl container= new VoodooControl ("ul", "xpath", "//table[@class='table table-striped dataTable']//ul[@class='dropdown-menu left carettop']");
		int j = 1;
		
		for(FieldSet column : columns){
			String collabel = column.get("column title").toString();
			container.assertContains(collabel, true);
			VoodooControl check= new VoodooControl ("i", "xpath", "//table[@class='table table-striped dataTable']//ul[@class='dropdown-menu left carettop']//li["+ j +"]//i");
			check.assertExists(true);
			j++;
		}
				
		dataFldToggle= new VoodooControl ("button", "xpath", "//table[@class='table table-striped dataTable']//ul[@class='dropdown-menu left carettop']//button[1]");
		dataFldToggle.click();
		VoodooControl firColumn= new VoodooControl ("th", "xpath", "//table[@class='table table-striped dataTable']//thead//th[@data-fieldname ='"+ columns.get(0).get("column title").toString() +"']");
		firColumn.assertExists(false);
		String data = myAccount.recordData.get(columns.get(0).get("column title").toString().toLowerCase());
		
		VoodooControl lists= new VoodooControl ("th", "xpath", "//table[@class='table table-striped dataTable']//tbody");		
		lists.assertContains(data, false);		
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		dataFldToggle.click();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}

