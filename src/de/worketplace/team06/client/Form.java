package de.worketplace.team06.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public abstract class Form extends Page {
	protected HorizontalPanel createHeadlineWithCloseButton(final String text, boolean isFirstPageElement) {
		HorizontalPanel hp = new HorizontalPanel();
		HTML sh = new HTML();
		if (isFirstPageElement) {
			sh.setHTML("<h1 class=\"first-element\">"+text+"</h1>");			
		} else {
			sh.setHTML("<h1>"+text+"</h1>");
		}
		Button closeButton = new Button("X");
		closeButton.setStyleName("close-button");
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainPanel.closeForm();
			}
		});
		hp.add(closeButton);
		hp.add(sh);
		return hp;
	}

	protected void renderFormSuccess() {
		ClientsideSettings.getCurrentView().loadData();
		mainPanel.closeForm();
	}
}