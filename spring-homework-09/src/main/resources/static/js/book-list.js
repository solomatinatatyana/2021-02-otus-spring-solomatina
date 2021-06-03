var filterBookForm;
$(document).ready(function () {
    getBooks();
    filterBookForm = $("#filter-form");
    filterBookForm.submit(function () {
        filterBook();
    });
});

function getBooks() {
    $.ajax({
        type: "GET",
        url: "/api/book",
        success: function (data) {
            var tableBody = $("#table-books");
            tableBody.empty();
            $(data).each(function (index,book) {
                tableBody.append('<tr>' +
                    '<td>'+book.title + '</td>' +
                    '<td>'+book.author.fullName + '</td>' +
                    '<td>'+book.genre.name + '</td>' +
                    '<td> ' +
                        '<ul>' +
                            '<li>' +
                                '<button class="mt-1 main-button btn btn-sm btn-secondary" id="edit_'+book.id+'" onclick="editBook('+book.id+')">Изменить</button>' +
                            '</li>'+
                            '<li>' +
                                '<button class="mt-1 main-button btn btn-sm btn-secondary" type="submit" id="delete_'+book.id+'" onclick="deleteBook('+book.id+')">Удалить</button>' +
                            '</li>'+
                        '</ul>'+
                    '</td>' +
                '</tr>');
            })
        }
    });
}

function editBook(bookId){
    var book = window.location.href="/book/"+bookId+"/edit";
    window.location.replace(book);
}

function deleteBook(bookId) {
    $.ajax({
        type: "DELETE",
        url: "/api/book/" + bookId,
        success: function (result) {
            console.log(result);
            getBooks();
        },
        error: function (e) {
            console.log(e);
        }
    })
}

function filterBook() {
    var book = {
        author : $("#author").val(),
        genre : $("#genre").val()
    };
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/book/filter?"+ $.param(book),
        dataType: 'json',
        cache: false,
        success: function (response) {
            $(response).after('<div>text</div>')
        },
        fail: function () {
            console.log("error");
        }
    })
}
