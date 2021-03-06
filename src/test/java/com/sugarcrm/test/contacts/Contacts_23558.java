package com.sugarcrm.test.contacts;

import org.junit.Test;

import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.test.SugarTest;

public class Contacts_23558 extends SugarTest {
	ContactRecord con1;
	Subpanel contactSub;
	DataSource ds;
	public void setup() throws Exception {
		sugar.login();
		con1 = (ContactRecord) sugar.contacts.api.create();
		con1.navToRecord();                
	}

	/** 
	 * Verify that the new created contact is displayed in "Direct Reports" sub-panel
	 * @throws Exception
	 */
	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");	
		
		ds= testData.get(testName);
		contactSub = new Subpanel(sugar.contacts);
		contactSub.addRecord();
        sugar.contacts.createDrawer.getEditField("lastName").set(ds.get(0).get("lastName"));
        sugar.contacts.createDrawer.save();                
        // TODO VOOD-609
        new VoodooControl("a", "css", "div[data-voodoo-name='Contacts'] td[data-type='fullname'] a").assertContains(ds.get(0).get("lastName"), true);
        
		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {		
		sugar.contacts.api.deleteAll();
		sugar.logout();
	}
}