package com.sugarcrm.test.users;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.UserRecord;
import com.sugarcrm.test.SugarTest;

public class User_create extends SugarTest {
	public void setup() throws Exception {
		sugar.login();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		UserRecord user1 = (UserRecord)sugar.users.create();
		user1.verify();

		VoodooUtils.voodoo.log.info(testName + " complete.");
	}

	public void cleanup() throws Exception {
		sugar.logout();
	}
}
