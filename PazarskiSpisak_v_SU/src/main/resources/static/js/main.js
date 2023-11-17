function myFunction() {
    let x = document.getElementById("myTopnav");
    if (x.className === "navbar") {
        x.className += " responsive";
    } else {
        x.className = "navbar";
    }
}


//надолу са експерименти за добавяне не съставка посредством JS, да ги изтрия после ако не намеря начин да върна JS Мап-а към контролера

//const altPerMainTextElList = document.getElementsByClassName("alt-per-main-unit");
//const mainUnitOfMeasureIngredient = document.getElementById("mainUnitOfMeasurement");
//const altPerMainTextEl = document.getElementById("alt-per-main-unit");
//let selectedMainUnitOfMeasure;
//
//mainUnitOfMeasureIngredient.onchange = (ev) => {
//    let selectedIndex = mainUnitOfMeasureIngredient.selectedIndex;
//    console.log("mainUnitOfMeasureIngredient selected index: " + selectedIndex);
//    let selectedOption = mainUnitOfMeasureIngredient.options[selectedIndex];
//    console.log("mainUnitOfMeasureIngredient selected option text: " + selectedOption.text);
//
//    selectedMainUnitOfMeasure = selectedOption.text;
//
//     if(selectedIndex > 0){
//        altPerMainTextEl.innerHTML = selectedOption.text;
//
//        for (let i = 0; i < altPerMainTextElList.length; i++) {
//                    altPerMainTextElList[i].innerHTML = selectedOption.text;
//                }
//
//        } else if(selectedIndex == 0){
//        altPerMainTextEl.innerHTML = "осн.м.ед";
//
//        for (let i = 0; i < altPerMainTextElList.length; i++) {
//                 altPerMainTextElList[i].innerHTML = "осн.м.ед";
//              }
//        }
//}
//
//
//let altMUnitDropDownList = document.getElementById('ingredientAlternativeMUnit');
//
//let currentlySelectedAltMUnit;
//
//altMUnitDropDownList.onchange = (ev) => {
//    let selectedIndex = altMUnitDropDownList.selectedIndex;
//    console.log("Selected index is: " + selectedIndex);
//    let selectedOption = altMUnitDropDownList.options[selectedIndex];
//    console.log("Selected option is: " + selectedOption.outerHTML);
//    console.log("Selected value is: " + selectedOption.value);
//    console.log("Selected text is: " + selectedOption.text);
//
//    currentlySelectedAltMUnit = selectedOption.text;
//}
//
//let altMValue = document.getElementById('alt-m-qty');
//let inputAltValue;
//
//altMValue.onchange = (ev) => {
//inputAltValue  = altMValue.value;
//console.log("Input value is: " + inputAltValue);
//
//}
//
//const mapOfAlternativeMUnitsForIngredient = new Map();
//
//
//function insertRowAndSelection() {
//
//let errorMessageEl = document.getElementById("js-control-adminProductAdd-msg");
//
//    if(mapOfAlternativeMUnitsForIngredient.size> 0 && mapOfAlternativeMUnitsForIngredient.has(currentlySelectedAltMUnit)){
//        errorMessageEl.classList.add("unhidden");
//        console.log("duplicate MValue chosen");
//    }else if(altMUnitDropDownList.selectedIndex == 0 || altMValue <= 0 || altMValue > 99999){
//        errorMessageEl.classList.add("unhidden");
//        console.log("smt went wrong")
//    } else if(altMUnitDropDownList.selectedIndex > 0 && inputAltValue > 0 && inputAltValue < 100000){
//
//            errorMessageEl.classList.remove("unhidden");
//
//            const tableEl = document.getElementById("alternativeProductMeasures");
//
//            let rowList = tableEl.rows;
//            let rowCount = rowList.length;
//
//            const templateRowEl = document.getElementById('template-row').content.firstElementChild;
//            const dropdownRowEl = document.getElementById('dropFields');
//
//            let newRow = templateRowEl.cloneNode(true);
//
//            newRow.querySelector(":scope > .alt-measure-unit").innerHTML = currentlySelectedAltMUnit;
//            newRow.querySelector(":scope > .alt-measure-qty").innerHTML = inputAltValue;
//            if(selectedMainUnitOfMeasure != undefined){
//                newRow.querySelector(":scope > .alt-per-main-unit").innerHTML = selectedMainUnitOfMeasure;
//            }
//
//            const parentTableBody = document.getElementById("dropFields").parentNode;
//
//            parentTableBody.insertBefore(newRow, dropdownRowEl);
//
//            mapOfAlternativeMUnitsForIngredient.set(currentlySelectedAltMUnit, inputAltValue);
//
//    }
//
//}
//
//
//function deleteRowAndContent(el){
//
//    const keyToRemoveFromMap = el.parentElement.parentElement.querySelector(":scope > .alt-measure-unit").textContent;
//    mapOfAlternativeMUnitsForIngredient.delete(keyToRemoveFromMap);
//
//    el.closest(".row-pr-unit-qty-act").remove();
//
//    }

//надолу е за да сменя осн.м.ед в избраното
//да го направя да се лоудва само при зареждане на страницата за добавяне на съставка
//може би с нещо такова: if (window.location.href.match('/admin/ingredient/add или пък admin-ingredient-add.html') != null)


