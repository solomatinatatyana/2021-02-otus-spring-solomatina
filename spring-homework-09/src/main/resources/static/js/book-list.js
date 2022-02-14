let filterBookForm;
$(document).ready(function () {
    getBooks();
    filterBookForm = $("#filter-form");
    filterBookForm.submit(function (event) {
        event.preventDefault();
        getBooks();
    });
});

function getBooks() {
    let book = {
        author : $("#author").val(),
        genre : $("#genre").val()
    };
    $.ajax({
        type: "GET",
        url: "/api/book",
        data:{author: book.author, genre: book.genre},
        success: function (data) {
            let tableBody = $("#table-books");
            tableBody.empty();
            $(data).each(function (index,book) {
                tableBody.append('<tr>' +
                    '<td>'+book.title + '</td>' +
                    '<td>'+book.author.fullName + '</td>' +
                    '<td>'+book.genre.name + '</td>' +
                    '<td>'+
                        '<ul>'+
                            '<li>'+ book.comments[0].commentText +'</li>' +
                        '</ul>'+
                    '</td>' +
                    '<td align="center">'+book.avgRating + '</td>' +
                    '<td> ' +
                    '<ul>' +
                        '<li>' +
                            '<a href="/book/'+book.id+'/edit">' +
                                '<button class="mt-1 main-button btn btn-sm btn-secondary" id="edit_'+book.id+'">Изменить</button>' +
                            '</a>' +
                        '</li>'+
                        '<li>' +
                            '<button class="mt-1 main-button btn btn-sm btn-secondary" type="submit" id="delete_'+book.id+'" onclick="deleteBook(&quot;'+book.id+'&quot;)">Удалить</button>' +
                        '</li>'+
                    '</ul>'+
                    '</td>' +
                    '</tr>');
            })
        }
    });
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

/*function getComments(comments){
    var elements = $();
    for(x=1; comments.length; x++){
       elements = elements.add('<li>'+comments[x].commentText+'</li>');
    }
    return elements;
}*/
