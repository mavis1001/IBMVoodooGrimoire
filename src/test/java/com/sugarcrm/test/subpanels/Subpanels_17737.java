package com.sugarcrm.test.subpanels;

import org.junit.Test;
import com.sugarcrm.test.SugarTest;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;

public class Subpanels_17737 extends SugarTest {
	ContactRecord myContact;
	CaseRecord myCase;
	// Common VoodooControl references
	VoodooControl caseRow;

	public void setup() throws Exception {
		// Initialize the common Control references
		caseRow = new VoodooControl("div", "css",
				"div[data-voodoo-name=Cases] table tbody tr:nth-of-type(1) .fld_name.list div");

		sugar.login();

		myContact = (ContactRecord) sugar.contacts.api.create();
		sugar.accounts.api.create();

		// For the set up a Case is related to a Contact via the Subpanel
		// TODO : Change this to use the api lib once library supports creating
		// relationships via API.
		myCase = (CaseRecord) sugar.cases.create();

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(2000);

		myContact.navToRecord();
		Subpanel casesSubpanel = sugar.contacts.recordView.subpanels
				.get("Cases");

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		sugar.contacts.recordView.showDataView();

		casesSubpanel.linkExistingRecord();

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		// Select the first Case from the list, should be the only one created
		new VoodooControl("input", "css", ".fld_Cases_select.list input")
				.click();

		// Need to pause for the Search Select screen to close and focus
		// returned to the original screen
		VoodooUtils.pause(1000);

		caseRow.assertEquals(myCase.recordData.get("name"), true);
	}

	/**
	 * Verify the subpanel row level action - Unlink and cancel
	 * 
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		myContact.navToRecord();
		Subpanel casesSubpanel = sugar.contacts.recordView.subpanels
				.get("Cases");

		// TODO - This pause to be removed when VOOD-497 - Include Waits in the
		// common LIB Methods - is implemented
		VoodooUtils.pause(1000);

		casesSubpanel.unlinkRecord(1);

		// TODO This line is to be replaced when a general class exists for the
		// Confirm Delete pop-up
		new VoodooControl("a", "css", ".cancel").click();

		caseRow.assertEquals(myCase.recordData.get("name"), true);

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.cases.api.deleteAll();
		sugar.contacts.api.deleteAll();
		sugar.accounts.api.deleteAll();
		sugar.logout();
	}
}
