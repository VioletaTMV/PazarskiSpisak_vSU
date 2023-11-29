package com.pazarskispisak.PazarskiSpisak.service.schedulers;

import com.pazarskispisak.PazarskiSpisak.service.ShoppingListFromRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InactiveShopListRemovalScheduler {

    static LocalDate INACTIVE_SHOPLIST_DATE = LocalDate.now().minusMonths(1);
    private ShoppingListFromRecipesService shoppingListFromRecipesService;

    @Autowired
    public InactiveShopListRemovalScheduler(ShoppingListFromRecipesService shoppingListFromRecipesService) {
        this.shoppingListFromRecipesService = shoppingListFromRecipesService;
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void removeInactiveShopLists(){

        this.shoppingListFromRecipesService.findAndDeleteShopListsInactiveByLastAccessedDate(INACTIVE_SHOPLIST_DATE);


    }
}
