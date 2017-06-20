package de.worketplace.team06.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.worketplace.team06.client.ClientsideSettings;
import de.worketplace.team06.shared.WorketplaceAdministrationAsync;
import de.worketplace.team06.shared.bo.Marketplace;

public class MarketplaceForm extends Page {
	private WorketplaceAdministrationAsync worketplaceAdministration = ClientsideSettings
			.getWorketplaceAdministration();
	private Label nameLabel = new Label("Name");
	private TextBox nameInput = new TextBox();
	private Label beschreibungLabel = new Label("Beschreibung");
	private TextBox beschreibungInput = new TextBox();
	private Boolean shouldUpdate = false;
	private Marketplace toChangeMarketplace;

	public MarketplaceForm(Marketplace pToChangeMarketplace) {
		if (pToChangeMarketplace != null) {
			shouldUpdate = true;
			this.toChangeMarketplace = pToChangeMarketplace;
		}
	}

	public void run() {
		Grid form = new Grid(3, 2);
		form.setWidget(0, 0, nameLabel);
		form.setWidget(0, 1, nameInput);
		form.setWidget(1, 0, beschreibungLabel);
		form.setWidget(1, 1, beschreibungInput);
		if (shouldUpdate) {
			nameInput.setText(toChangeMarketplace.getTitle());
			beschreibungInput.setText(toChangeMarketplace.getDescription());
			final Button saveButton = new Button("Änderungen speichern");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					// TODO
					Window.alert("Clicked");
				}
			});
			final HorizontalPanel panel = new HorizontalPanel();
			panel.add(saveButton);
			final Button deleteButton = new Button("Diesen Marktplatz entfernen");
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					// TODO
					Window.alert("Clicked");
				}
			});
			panel.add(deleteButton);
			form.setWidget(2, 1, panel);
		} else {
			final Button saveButton = new Button("Neuen Marktplatz anlegen");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Window.alert("Clicked");
					if (beschreibungInput.getText().length() == 0) {
						Window.alert("Bitte beschreiben Sie Ihren Marktplatz genauer");
					} else if (nameInput.getText().length() == 0) {
						Window.alert("Bitte vergeben Sie einen Namen");
					} else {
						toChangeMarketplace.setDescription(beschreibungInput.getText());
						toChangeMarketplace.setTitle(nameInput.getText());
						worketplaceAdministration.saveMarketplace(toChangeMarketplace, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Es trat ein Fehler beim Speichern auf, bitte versuchen Sie es erneut");
							}
							public void onSuccess(Void result) {
								Window.alert("Der Marktplatz wurde gespeichert");
							}
						});
					}
				}
			});
			form.setWidget(2, 1, saveButton);
		}
		final VerticalPanel root = new VerticalPanel();
		root.add(createHeadline("Marktplatz bearbeiten"));
		root.add(form);
		RootPanel.get("content").add(root);
	}
}