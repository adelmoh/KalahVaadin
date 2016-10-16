package com;

import com.vaadin.event.Action;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import java.util.List;

/**
 * Created by M on 10/15/2016.
 */
public class PlayerLayout extends HorizontalLayout {


    private final Label player1Home;

    private KalahService kalahService = new KalahService();

    /**
     * to draw the player layout
     * @param player
     * @param isHomeFirst to set whether home is in the front or the end
     */
    public PlayerLayout(Player player, boolean isHomeFirst){
        super();

        setMargin(true);
        setSpacing(true);

        player1Home = new Label("<b>"+String.valueOf(player.getHome())+"</b>", ContentMode.HTML);
        player1Home.setImmediate(true);

        if(isHomeFirst){
            addComponent(player1Home);
        } else{
            addComponent(new Label("&nbsp;&nbsp;", ContentMode.HTML));
        }

        for(int i=0; i<player.getPits().size();i++){
            Integer pit = player.getPits().get(i);
            Button pitButton = new Button(String.valueOf(pit));
            pitButton.setEnabled(player.getEnabled() && pit != 0);
            pitButton.addClickListener(getPitButtonClickListener(player, i));
            addComponent(pitButton);
        }

        if(!isHomeFirst){
            addComponent(player1Home);
        }
    }

    private Button.ClickListener getPitButtonClickListener(final Player player, final int index) {
        return new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                Player winnder = kalahService.updateDashboard(player, index);
                DashboardView dashboardView = new DashboardView(player, player.getOtherPlayer());
                MainVaadinUI.getCurrent().getNavigator().addView(dashboardView.NAME, dashboardView);
                MainVaadinUI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);

                if(winnder != null){
                    Notification notification = new Notification("Winner is player"+winnder.getId(), Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(3000);
                    notification.show(Page.getCurrent());
                }
            }
        };
    }
}
