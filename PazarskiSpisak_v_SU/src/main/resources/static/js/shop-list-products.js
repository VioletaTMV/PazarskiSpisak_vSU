const charsToRemoveToGetOnlyTheSequenceNumberSL = "shop-list-ingredient-checkbox";
const strikethrough = "strikethrough";
//const pageURL = location.pathname = "/list/products";


    const checkBoxElList = document.getElementsByClassName('card-ingredient-checkbox');

    for (let i = 0; i < checkBoxElList.length; i++) {
        if (checkBoxElList[i].checked) {
//              const paragraphNodeEl = checkBoxElList[i].nextSibling;
              const paragraphEl = checkBoxElList[i].nextElementSibling;
              paragraphEl.classList.add(strikethrough);

        }
    }



function strikeUnstrike(idOfCheckboxEl){

    productCheckboxEl = document.getElementById(idOfCheckboxEl);

    let currentSequenceNum = idOfCheckboxEl.replace(charsToRemoveToGetOnlyTheSequenceNumberSL, "");

    textForTheCheckboxEl = document.getElementById('shop-list-ingredient-info' + currentSequenceNum);

    if (textForTheCheckboxEl.classList.contains(strikethrough)){
        textForTheCheckboxEl.classList.remove(strikethrough);
    } else {
        textForTheCheckboxEl.classList.add(strikethrough);
        hideChecked(document.getElementById('hideCheckedSlider'));
    }

}

function hideChecked(checkbox){

 if(checkbox.checked == true){

       const productCategoriesWrapperElList = document.getElementsByClassName("cart-ingredients-list-container");

       for (let i = 0; i < productCategoriesWrapperElList.length; i++){

            const innerProductRowElList  = productCategoriesWrapperElList[i].querySelectorAll("div.cart-ingredients-list-el");
            let counterChecked = 0;

           for (let i = 0; i < innerProductRowElList.length; i++){

           //find the input El by id
               const inputCheckboxEl = innerProductRowElList[i].querySelector("input.card-ingredient-checkbox");
               if (inputCheckboxEl.checked == true){
                    innerProductRowElList[i].classList.add("jsNoDisplay");
                    counterChecked += 1;
               }
           }
           //if allProductsFromCategory are checked, hide the parent category name wrapper.
           if (counterChecked == innerProductRowElList.length){
                  productCategoriesWrapperElList[i].classList.add("jsNoDisplay");
           }
       }

    }else{

         const productCategoriesWrapperElList = document.getElementsByClassName("cart-ingredients-list-container");

               for (let i = 0; i < productCategoriesWrapperElList.length; i++){

                          productCategoriesWrapperElList[i].classList.remove("jsNoDisplay");
               }

         const productRowElList = document.getElementsByClassName("cart-ingredients-list-el");

                for (let i = 0; i < productRowElList.length; i++){

                           productRowElList[i].classList.remove("jsNoDisplay");
                }
    }
}
