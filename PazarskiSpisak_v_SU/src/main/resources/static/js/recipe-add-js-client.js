
const charsToRemoveToGetOnlyTheSequenceNumber = "ingredientSelect";

const mapOfIngredientsWithQtyAndMeasureUnit = new Map();
let ingredientSelectEl;
let selectedIngredientId;
let measureUnitSelectEl;

function limitMeasurementUnitsDropdownOptionsByIngredientId(idOfSelectEl){

//    ingredientSelectEl = document.getElementById('ingredientSelect');
    ingredientSelectEl = document.getElementById(idOfSelectEl);

    let currentSequenceNum = idOfSelectEl.replace(charsToRemoveToGetOnlyTheSequenceNumber, "");

    measureUnitSelectEl = document.getElementById('unitOfMeasure' + currentSequenceNum);

    selectedIngredientId = ingredientSelectEl.value;
    let measureUnitOptionsArr = Object.values(measureUnitSelectEl.options);

   if (measureUnitOptionsArr.length > 1){
            for (let i = measureUnitOptionsArr.length-1; i > 0; i--){
            measureUnitSelectEl.remove(i);
            }
   }

    if(selectedIngredientId > 0){

        var requestOptions = {
          method: 'GET',
          redirect: 'follow'
        };

        fetch(`http://localhost:8080/products/${selectedIngredientId}`, requestOptions)
//долу променяме хттп адреса, за да работи на онлайн ендпойнта в Azure
//        fetch(`https://pazarskispisak.calmocean-7e102e43.westeurope.azurecontainerapps.io/products/${selectedIngredientId}`, requestOptions)
          .then(response => response.json())
          .then(jsonIngr => Object.entries(jsonIngr.acceptableMeasurementUnitsForRecipeDescriptionMap).forEach(([key, value]) => {

                                         measureUnitOptionsArr = Object.values(measureUnitSelectEl.options);

                                         const newOption = document.createElement("option");
                                         newOption.value = `${key}`;
                                         newOption.text = `${value}`;

                                         measureUnitSelectEl.add(newOption);

                                     }))

      .catch(error => console.log('error', error));

    }

 }



 //надолу са действията за бутона + и - ако се хендълват с JavaScript

// function insertRecipeIngredientRowAndSelection() {
//
// const errorMessage3FieldsEl = document.getElementById("js-control-3-fields-filled-msg");
// const errorMessageProductQtyConstrEl = document.getElementById("js-control-product-qty-constr-msg");
// const errorMessageProductSingleUsageEl = document.getElementById("js-control-product-single-usage-msg");
//
//    ingredientSelectEl = document.getElementById('ingredientSelect');
//    measureUnitSelectEl = document.getElementById('unitOfMeasure');
//    selectedIngredientId = ingredientSelectEl.value;
//    const selectedMeasureUnitId = measureUnitSelectEl.value;
//    const ingredientQtyEl = document.getElementById('prQty');
//    const inputIngredientQty  = ingredientQtyEl.value;
//
//     if(selectedIngredientId == 0 || selectedMeasureUnitId == 0 || !inputIngredientQty){
//              errorMessage3FieldsEl.classList.add("unhidden");
//              console.log("all fields are not filled")
//     else if(mapOfIngredientsWithQtyAndMeasureUnit.size> 0 && mapOfIngredientsWithQtyAndMeasureUnit.has(selectedIngredientId)){
//         errorMessageProductSingleUsageEl.classList.add("unhidden");
//         console.log("duplicate ingredient chosen");
//     }else if(inputIngredientQty <=0 || inputIngredientQty > 99999){
//              errorMessageProductQtyConstrEl.classList.add("unhidden");
//              console.log("qty must be more than 0 and no more than 99999")
//     } else {
//
//             errorMessage3FieldsEl.classList.remove("unhidden");
//             errorMessageProductQtyConstrEl.classList.remove("unhidden");
//             errorMessageProductSingleUsageEl.classList.remove("unhidden");
//
//             const tableEl = document.getElementById("ingredientsWithQtyAndMUtable");
//
//             let rowList = tableEl.rows;
//             let rowCount = rowList.length;
//
//             const templateRowEl = document.getElementById('template-row').content.firstElementChild;
//             const dropdownRowEl = document.getElementById('dropFields');
//
//             let newRow = templateRowEl.cloneNode(true);
//
//             newRow.querySelector(":scope > .alt-measure-unit").innerHTML = currentlySelectedAltMUnit;
//             newRow.querySelector(":scope > .alt-measure-qty").innerHTML = inputAltValue;
//             if(selectedMainUnitOfMeasure != undefined){
//                 newRow.querySelector(":scope > .alt-per-main-unit").innerHTML = selectedMainUnitOfMeasure;
//             }
//
//             const parentTableBody = document.getElementById("dropFields").parentNode;
//
//             parentTableBody.insertBefore(newRow, dropdownRowEl);
//
//             mapOfAlternativeMUnitsForIngredient.set(currentlySelectedAltMUnit, inputAltValue);
//
//     }
//
// }
//
//
// function deleteRowAndContent(el){
//
//     const keyToRemoveFromMap = el.parentElement.parentElement.querySelector(":scope > .alt-measure-unit").textContent;
//     mapOfAlternativeMUnitsForIngredient.delete(keyToRemoveFromMap);
//
//     el.closest(".row-pr-unit-qty-act").remove();
//
//     }