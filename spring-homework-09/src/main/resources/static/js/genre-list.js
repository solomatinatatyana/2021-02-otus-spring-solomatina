var addGenreForm;

$(document).ready(function () {
    getGenres();
    addGenreForm = $("#addGenreForm");
    addGenreForm.submit(function (event) {
        event.preventDefault();
        addGenre();
        });
});

function getGenres() {
    $.ajax({
        type: "GET",
        url: "/api/genre",
        success: function (data) {
            var tableBody = $("#table-genres");
            tableBody.empty();
            $(data).each(function (index,genre) {
                tableBody.append('<tr>' +
                    '<td>'+genre.name + '</td>' +
                    '<td> ' +
                        '<ul>' +
                            '<li>' +
                                '<a href="/genre/'+genre.id+'/edit">' +
                                     '<button class="mt-1 main-button btn btn-sm btn-secondary" id="edit_'+genre.id+'">Изменить</button>' +
                    '           </a>' +
                            '</li>'+
                            '<li>' +
                                '<button class="mt-1 main-button btn btn-sm btn-secondary" type="submit" id="delete_'+genre.id+'" onclick="deleteGenre(&quot;'+genre.id+'&quot;)">Удалить</button>' +
                            '</li>'+
                        '</ul>'+
                    '</td>' +
                    '</tr>')
            })
        }
    });
}

function addGenre() {
    var genre = {
        name : $("#genre-name").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/genre",
        data: JSON.stringify(genre),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("genre", data);
            getGenres();
        },
        fail: function () {
            console.log("error");
        }
    })
}

function deleteGenre(genreId) {
    $.ajax({
        type: "DELETE",
        url: "/api/genre/" + genreId,
        success: function (result) {
            console.log(result);
            getGenres();
        },
        error: function (e) {
            console.log(e);
        }
    })
}
