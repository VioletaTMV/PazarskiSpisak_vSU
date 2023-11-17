package com.pazarskispisak.PazarskiSpisak.models.enums;

public enum RecipeCategoryEnum {

    // by course
    SALAD ("Салати"),
    SOUP ("Супи"),
    APPETIZER ("Предястия"),
    MAIN_COURSE ("Основни"),
    DESSERT ("Десерти"),
    SIDE_DISH ("Гарнитури"),
    DRINK ("Напитки"),

    //by type
    SANDWICH ("Сандвичи"),
    PASTA_RICE ("Паста и ориз"),
    SAUCE ("Сосове"),
    CAKE_COOKIE ("Кейкове и бисквити"),
    PIZZA ("Пици"),
    BANITZA_BREAD ("Баници и питки"),
    KIDS ("Детска кухня"),
    PRESERVES ("Туршии и сладка"),
    VEGAN ("Веган"),

    //by cooking method
    FRYING ("Пържене"),
    STEAMING ("На пара"),
    BOILING ("Варене"),
    BAKING ("Печене"),
    BBQ ("Барбекю"),
    RAW ("Сурово"),
    BREADING("Паниране"),

    // by cuisine
    FRENCH ("Френска"),
    ITALIAN ("Италианска"),
    ASIAN ("Азиатска"),
    MEDITERRANEAN ("Средиземноморска"),
    AMERICAN ("Американска"),
    MEXICAN ("Мексиканска"),
    BULGARIAN ("Българска"),

    // by main ingredient
    VEGETABLES ("Зеленчуци"),
    LEGUMINOUS ("Бобови"),
    PORK ("Свинско"),
    VEAL_BEEF ("Телешко и говеждо"),
    LAMB_MUTTON ("Агнешко и овче"),
    MINCED_MEAT ("С кайма"),
    POULTRY ("Пилешко и пуешко"),
    RABBIT ("Заешко"),
    FISH_SEAFOOD ("С риба и морски дарове"),
    EGGS ("С яйца"),
    DAIRY_PRODUCTS ("С млечни продукти"),
    GAME ("Дивеч"),
    DUCK_GOOSE ("Патешко и гъше"),
    SAUSAGES_MEAT_DELICACY("Колбаси и месни деликатеси"),
    GIBLETS ("Дреболии");


    private final String CategoryBG;

    RecipeCategoryEnum(String categoryBG) {
        CategoryBG = categoryBG;
    }

    public String getCategoryBG() {
        return CategoryBG;
    }
}
