package com.sugarcrm.test.activitystream;

import org.junit.Ignore;
import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.test.SugarTest;

public class Activitystream_17457_1 extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
}

	/**
	 * Show tip message in the comments input box 
	 * @throws Exception
	 *
	 */
	@Ignore("This may not be a valid test, the tool tips visibility is handled by the browser and not our product")
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");
		DataSource ds = testData.get(testName);
		String tip = ds.get(0).get("tip");
		
		//TODO VDDD-474 need replace these controls after lib file is done 
		VoodooUtils.voodoo.log.info("Check tool tip message on home 'module' activity stream view");
		sugar.navbar.navToModule("Home");
		new VoodooControl("b", "css", "li[data-module='Home'] a[data-toggle='dropdown']").click();
		VoodooUtils.pause(800);
		new VoodooControl ("a", "css", "div.dropdown-menu.scroll a[data-route='#activities']").click();
		VoodooUtils.pause(800);
		sugar.accounts.listView.activityStream.createComment(ds.get(0).get("post"));
		VoodooUtils.pause(3000);
		// The use of sugar.accounts. is because we do not have a
		// HomeModule.java file for support
		// When HomeModule.java is available then the following lines should be
		// updated to use it.
		sugar.accounts.listView.activityStream.getControl("streamInput").assertAttribute("data-placeholder", tip);
		
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}
	
	public void cleanup() throws Exception {
		sugar.logout();
	}
}