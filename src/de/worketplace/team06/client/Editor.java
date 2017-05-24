package de.worketplace.team06.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.worketplace.team06.client.ClientsideSettings;
import de.worketplace.team06.shared.WorketplaceAdministrationAsync;
import de.worketplace.team06.shared.bo.OrgaUnit;
import de.worketplace.team06.shared.bo.Person;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Editor implements EntryPoint {
	WorketplaceAdministrationAsync worketplaceAdministration = null;

	Label nameLabel = new Label();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if (worketplaceAdministration == null) {
			worketplaceAdministration = ClientsideSettings.getWorketplaceAdministration();
		}
		
		RootPanel.get("sayHello").add(nameLabel);

		worketplaceAdministration.getTestUnit(new GetOrgaUnitCallback());
	}

	private class GetOrgaUnitCallback implements AsyncCallback<Person> {
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Person result) {
			if (result != null) {
				nameLabel.setText(result.getFirstName());
			}
		}

	}
}