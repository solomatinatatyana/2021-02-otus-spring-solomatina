var filterBookForm;

$(document).ready(function () {
    filterBookForm = $("#filter-form");
    filterBookForm.submit(function () {
        filterBook();
    });
});


function filterBook() {
    var book = {
        author : $("#author").val(),
        genre : $("#genre").val()
    };
    $.get("/api/book/filter",{author: book.author, genre: book.genre}, function () {

    })
    /*$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/book/filter?"+ $.param(book),
        dataType: 'json',
        cache: false,
        success: function (response) {
            getBooks();
        },
        fail: function () {
            console.log("error");
        }
    })*/
}

