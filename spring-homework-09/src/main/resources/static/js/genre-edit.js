var editGenreForm;
var idGenreField;
var nameField;

$(document).ready(function () {
    idGenreField = $("#id-input");
    nameField = $("#name-input");
    editGenreForm = $("#genre-edit-form");
    editGenreForm.submit(function (event) {
        event.preventDefault();
        editGenre();
    })
});

function editGenre() {
    var genre = {
        name: nameField.val()
    };

    $.ajax({
        type: "PATCH",
        contentType: "application/json",
        url: "/api/genre/"+idGenreField.val(),
        data: JSON.stringify(genre),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("genre", data);
            window.location.replace("/genre")
        },
        fail: function () {
            console.log("error");
        }
    })
}
