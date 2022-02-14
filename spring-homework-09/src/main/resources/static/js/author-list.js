let addAuthorForm;

$(document).ready(function () {
    getAuthors();
    addAuthorForm = $("#addAuthorForm");

    addAuthorForm.submit(function (event) {
        event.preventDefault();
        addAuthor();
    });
});

function getAuthors() {
    $.ajax({
        type: "GET",
        url: "/api/author",
        success: function (data) {
            var tableBody = $("#table-authors");
            tableBody.empty();
            $(data).each(function (index,author) {
                tableBody.append('<tr>' +
                    '<td>'+author.fullName + '</td>' +
                    '<td> ' +
                        '<ul>' +
                            '<li>' +
                                '<a href="/author/'+author.id+'/edit">' +
                                    '<button class="mt-1 main-button btn btn-sm btn-secondary" id="edit_'+author.id+'">Изменить</button>' +
                                '</a>' +
                            '</li>'+
                            '<li>' +
                                '<button class="mt-1 main-button btn btn-sm btn-secondary" type="button" id="delete_'+author.id+'" onclick="deleteAuthor(&quot;'+author.id+'&quot;)">Удалить</button> ' +
                            '</li>'+
                        '</ul>'+
                    '</td>' +
                    '</tr>')
            })
        }
    });
}

function addAuthor() {
    var author = {
        fullName : $("#fullName").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/author",
        data: JSON.stringify(author),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("author", data);
            getAuthors();
        },
        fail: function () {
            console.log("error");
        }
    })
}

function deleteAuthor(authorId) {
    $.ajax({
        type: "DELETE",
        url: "/api/author/" + authorId,
        success: function (result) {
            console.log(result);
            getAuthors();
        },
        error: function (e) {
            console.log(e);
        }
    })
}
