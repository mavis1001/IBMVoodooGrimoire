package com.sugarcrm.test.accounts;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.test.SugarTest;

public class Accounts_17598 extends SugarTest {
	AccountRecord myAccount;
	
	public void setup() throws Exception {
		sugar.login();
		myAccount = (AccountRecord)sugar.accounts.api.create();
	}

	/**
	 * Verify the deleted records not be shown in the recently viewed list 
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {		
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		
		myAccount.navToRecord();		
		sugar.accounts.navToListView();
	
		sugar.navbar.clickModuleDropdown(sugar.accounts);
		//TODO VOOD-508 need lib support for Recently Viewed container in the navbar
		new VoodooControl("div", "css", "li[data-module='Accounts'] .recentContainer.hide")
			.assertElementContains(myAccount.getRecordIdentifier(), true);

		sugar.accounts.api.deleteAll();
		VoodooUtils.refresh();
		sugar.accounts.listView.getControl("moduleTitle").waitForVisible();
		sugar.navbar.clickModuleDropdown(sugar.accounts);
		new VoodooControl("div", "css", "li[data-module='Accounts'] .recentContainer.hide")
			.assertElementContains(myAccount.getRecordIdentifier(), false);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
