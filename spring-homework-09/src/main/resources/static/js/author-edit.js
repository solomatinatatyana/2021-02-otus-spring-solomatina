var editAuthorForm;
var idAuthorField;
var fullNameField;

$(document).ready(function () {
    idAuthorField = $("#id-input");
    fullNameField = $("#fullName-input");
    editAuthorForm = $("#author-edit-form");
    editAuthorForm.submit(function (event) {
        event.preventDefault();
        editAuthor();
    })
});

function editAuthor() {
    var author = {
        fullName: fullNameField.val()
    };

    $.ajax({
        type: "PATCH",
        contentType: "application/json",
        url: "/api/author/"+idAuthorField.val(),
        data: JSON.stringify(author),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("author", data);
            window.location.replace("/author")
        },
        fail: function () {
            console.log("error");
        }
    })
}