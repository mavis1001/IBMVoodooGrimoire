package com.sugarcrm.test.notes;

import org.junit.Test;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.NoteRecord;
import com.sugarcrm.test.SugarTest;


import static org.junit.Assert.assertEquals;

public class Notes_delete extends SugarTest {
	NoteRecord myNote;
	
	public void setup() throws Exception {
		sugar.login();
		myNote = (NoteRecord)sugar.notes.api.create();
	}

	@Test
	public void execute() throws Exception {
		VoodooUtils.voodoo.log.info("Running " + testName + "...");

		// Delete the note using the UI.
		myNote.delete();
		
		// Verify the note was deleted.
		sugar.notes.navToListView();
		assertEquals(VoodooUtils.contains(myNote.getRecordIdentifier(), true), false);

		VoodooUtils.voodoo.log.info(testName + "complete.");
	}

	public void cleanup() throws Exception {
		sugar.notes.api.deleteAll();
		sugar.logout();
	}
}
