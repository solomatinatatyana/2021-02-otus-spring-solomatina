var addBookForm;

$(document).ready(function () {
    addBookForm = $("#addBookForm");
    addBookForm.submit(function (event) {
        event.preventDefault();
        addBook();
    })
});

function addBook() {
    var book = {
        title : $("#book-title").val(),
        author: {
            fullName: $("#book-author").val(),
        },
        genre:{
            name: $("#book-genre").val()
        }
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/book",
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
