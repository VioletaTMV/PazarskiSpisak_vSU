let altPerMainTextElList = document.getElementsByClassName("alt-per-main-unit");
const mainUnitOfMeasureIngredientEl = document.getElementById("mainUnitOfMeasurement");
//const altPerMainTextEl = document.getElementById("alt-per-main-unit");
let selectedMainUnitOfMeasure;
let selectedMainUnitOfMeasureValue;
let selectedIndex;


let selectedAltUnitsOfMeasureValuesList = new Array();
const tableEl = document.getElementById("alternativeProductMeasures");
let altMUValueMap = new Map();
let alternMeasureElList = document.getElementsByClassName("alternMeasure");
let altMUValueElList = document.getElementsByClassName("altMUValue");
const availableSelectionsForShoppingListDropdown = new Array();
const shopListDropDownSelectEl = document.getElementById('item-shopping-display-measure-unit');
const shopListDropDownOptionsList = shopListDropDownSelectEl.options;
const emptyShopListValue = 'EMPTY';
const defaultNotChoosableValueForChoose = '';

//availableSelectionsForShoppingListDropdown.push(emptyShopListValue, defaultNotChoosableValueForChoose);

function getMainUnitOfMeasurement() {

selectedIndex = mainUnitOfMeasureIngredientEl.selectedIndex;
    console.log("mainUnitOfMeasureIngredient selected index: " + selectedIndex);
    const selectedOption = mainUnitOfMeasureIngredientEl.options[selectedIndex];
    console.log("mainUnitOfMeasureIngredient selected option text: " + selectedOption.text);

    selectedMainUnitOfMeasure = selectedOption.text;

    removeItemOnce(availableSelectionsForShoppingListDropdown, selectedMainUnitOfMeasureValue);

     if(selectedIndex > 0){

        for (let i = 0; i < altPerMainTextElList.length; i++) {
                    altPerMainTextElList[i].innerHTML = selectedMainUnitOfMeasure;
        }
            selectedMainUnitOfMeasureValue = mainUnitOfMeasureIngredientEl.options[selectedIndex].value;

            } else if(selectedIndex == 0){

                    for (let i = 0; i < altPerMainTextElList.length; i++) {
                             altPerMainTextElList[i].innerHTML = "";
                          }
                  }

}

mainUnitOfMeasureIngredientEl.onchange = (ev) => {

    getMainUnitOfMeasurement();
       showOnlyOneOfTheAlreadySelectedMainOrAltUnits();
}

//долното върши работа ако имаме лист с няколко селекта
//tableEl.onload = functionAlternateText();

//function functionAlternateText() {
//
//  selectedIndex = mainUnitOfMeasureIngredientEl.selectedIndex;
//  const selectedOption = mainUnitOfMeasureIngredientEl.options[selectedIndex];
//  selectedMainUnitOfMeasure = selectedOption.text;
//
//      if(selectedIndex > 0){
//
//             for (let i = 0; i < altPerMainTextElList.length; i++) {
//                 altPerMainTextElList[i].innerHTML = selectedMainUnitOfMeasure;
//             }
//
//
//      } else if(selectedIndex == 0){
//
//                for (let i = 0; i < altPerMainTextElList.length; i++) {
//                         altPerMainTextElList[i].innerHTML = "";
//                      }
//      }
//
//      showOnlyOneOfTheAlreadySelectedMainOrAltUnits();
//}

// долното допълва във варианта с лист, за да се показва само някоя от вече избраните основна или алтернативни мерни единици в последното падащо меню за пазарския списък

//function showOnlyOneOfTheAlreadySelectedMainOrAltUnits(){
//
//const selectedMainUnitOfMeasureValue = mainUnitOfMeasureIngredientEl.options[selectedIndex].value;
//
//let selectedAltUnitOfMeasureList = new Array();
//let altMUnitDropDownElList = document.getElementsByClassName('ingredientAlternativeMUnit');
//
//const shopListDropDownSelectEl = document.getElementById('item-shopping-display-measure-unit');
//const shopListDropDownOptionsList = shopListDropDownSelectEl.options;
//
//if (!availableSelectionsForShoppingListDropdown.includes(selectedMainUnitOfMeasureValue)) {
//availableSelectionsForShoppingListDropdown.push(selectedMainUnitOfMeasureValue);
//}
//
//
//for (let i = 0; i < altMUnitDropDownElList.length; i++) {
//
//        const optionsAvailableForElement = altMUnitDropDownElList[i].options;
//        const selectedIndexOfAltOption = optionsAvailableForElement.selectedIndex;
//        const selectedValueOfAltOption = optionsAvailableForElement[selectedIndexOfAltOption].value;
//
//            if(!selectedAltUnitOfMeasureList.includes(selectedValueOfAltOption)){
//            selectedAltUnitOfMeasureList.push(selectedValueOfAltOption);
//            }
//
//            if (!availableSelectionsForShoppingListDropdown.includes(selectedValueOfAltOption)) {
//            availableSelectionsForShoppingListDropdown.push(selectedValueOfAltOption);
//            }
//}
//
//let valueChanged;
//let indexOfValueChanged =-1;
//
//for(i=0; i<availableSelectionsForShoppingListDropdown.length; i++ ){
//    if(availableSelectionsForShoppingListDropdown[i] != emptyShopListValue &&
//    availableSelectionsForShoppingListDropdown[i] != defaultNotChoosableValueForChoose &&
//    availableSelectionsForShoppingListDropdown[i] != selectedMainUnitOfMeasureValue &&
//    !selectedAltUnitOfMeasureList.includes(availableSelectionsForShoppingListDropdown[i]) ){
//        valueChanged = availableSelectionsForShoppingListDropdown[i];
//        indexOfValueChanged = i;
//    }
//}
//
//if(valueChanged !== null){
//removeItemOnce(availableSelectionsForShoppingListDropdown, valueChanged)
//}
//
//for(let i=0; i < shopListDropDownOptionsList.length; i++){
//
//   const currentShopListValue = shopListDropDownOptionsList[i].value;
//
//    if(availableSelectionsForShoppingListDropdown.includes(currentShopListValue) ){
//    shopListDropDownOptionsList[i].classList.add('unhide');
//    } else {
//       shopListDropDownOptionsList[i].classList.remove('unhide');
//    }
//}
//
//}

window.onload = getMainUnitOfMeasurement();
window.onload = getAlternativeMUnitValuesNotNull();

function showOnlyOneOfTheAlreadySelectedMainOrAltUnits(){

availableSelectionsForShoppingListDropdown.splice(0, availableSelectionsForShoppingListDropdown.length);
availableSelectionsForShoppingListDropdown.push(emptyShopListValue, defaultNotChoosableValueForChoose);

availableSelectionsForShoppingListDropdown.push(selectedMainUnitOfMeasureValue);

//for(i=0; i<altMUValueMap.size; i++){
//availableSelectionsForShoppingListDropdown.push(altMUValueMap[i].);
//}

for (let key of altMUValueMap.keys()) {
    availableSelectionsForShoppingListDropdown.push(key);

}

for(let i=0; i < shopListDropDownOptionsList.length; i++){

   const currentShopListValue = shopListDropDownOptionsList[i].value;

    if(availableSelectionsForShoppingListDropdown.includes(currentShopListValue) ){
    shopListDropDownOptionsList[i].classList.add('unhide');
    } else {
       shopListDropDownOptionsList[i].classList.remove('unhide');
    }
}

}



 function getAlternativeMUnitValuesNotNull() {

selectedAltUnitsOfMeasureValuesList.splice(0, selectedAltUnitsOfMeasureValuesList.length);
altMUValueMap.clear();

for(i = 0; i < alternMeasureElList.length; i++ ){
const currentAltMU = alternMeasureElList[i].value;
const currentAltMUValue = altMUValueElList[i].value;


    if(currentAltMUValue != null && currentAltMUValue != 0){
    selectedAltUnitsOfMeasureValuesList.push(currentAltMU);
    altMUValueMap.set(currentAltMU, currentAltMUValue);
    }

}
showOnlyOneOfTheAlreadySelectedMainOrAltUnits();
}




function removeItemOnce (arr, value){
let index = arr.indexOf(value);
  if (index > -1) {
    arr.splice(index, 1);
  }
  return arr;

}
