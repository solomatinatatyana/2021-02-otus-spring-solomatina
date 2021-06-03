var editBookForm;
var idBookField;
var titleBookField;
var authorField;
var genreFileld;

$(document).ready(function () {
    idBookField = $("#id-input");
    titleBookField = $("#title-input");
    authorField = $("#author-input");
    genreFileld = $("#genre-input");
    editBookForm = $("#book-edit-form");
    editBookForm.submit(function (event) {
        event.preventDefault();
        editBook();
    })
});

function editBook() {
    var book = {
        title : titleBookField.val(),
        author: {
            fullName: authorField.val(),
        },
        genre:{
            name: genreFileld.val()
        }
    };

    $.ajax({
        type: "PATCH",
        contentType: "application/json",
        url: "/api/book/"+idBookField.val(),
        data: JSON.stringify(book),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("book", data);
            window.location.replace("/book")
        },
        fail: function () {
            console.log("error");
        }
    })
}
