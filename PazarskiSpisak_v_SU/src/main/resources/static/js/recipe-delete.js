
const softDeleteDiv = document.getElementById('recipe-actions');
 const confirmDeleteDiv = document.getElementById('confirm-delete-action-js');


function confirmDeletionForRecipe(){

  softDeleteDiv.classList.add("jsNoDisplay");
  confirmDeleteDiv.classList.add("unhide");

}

function CancelAndReturnToPreviousScreen(){

    confirmDeleteDiv.classList.remove("unhide");
    softDeleteDiv.classList.remove("jsNoDisplay");

}




