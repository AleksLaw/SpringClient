$(document).ready(function () {
    $('#add_button').on('click', function () {
//Добавление пользователя
       // $.post("http://localhost:8081/admin/add", $("#addNewUser").serialize())
        $.post("/admin/add", $("#addNewUser").serialize())
            .done(function (data) {
                var id = data.id,
                    name = data.name,
                    lastName = data.lastName,
                    age = data.age,
                    email = data.email,
                    userRoles = data.userRoles;
                $("tbody:first").append(
                    `<tr>
                       <td>${id}</td><td>${name}</td><td>${lastName}</td><td>${age}</td><td>${email}</td><td>${userRoles}</td>
                       <td><input onclick='editUser(this)' type='button' class='btn btn-primary' value='Edit'></td>
                       <td><input onclick='deleteUser(this)' type='button' class='btn btn-danger' value='Delete'></td>
                     </tr>`
                );
                $('#addNewUser').trigger('reset');
                $('#userTablePage').trigger('click');
            })
    })
});


function editUser(ths) {
    let tr = ths.parentNode.parentNode;
    let id = tr.getElementsByTagName("td")[0].innerHTML;
    $.get(`/admin/get/${id}`)
        .done(function (data) {
            $('#idE').attr('value', data.id)
            $('#nameE').attr('placeholder', data.name)
            $('#lastNameE').attr('placeholder', data.lastName)
            $('#ageE').attr('placeholder', data.age)
            $('#emailE').attr('placeholder', data.email)
            $('#modalEdit').modal('show');
            $('#edit_button').on('click', function () {
                var disabled = $('#userEditModal').find(':input:disabled').removeAttr('disabled');
                var serialized = $('#userEditModal').serialize();
                disabled.attr('disabled', 'disabled');
                $('#modalEdit').modal('hide');
                $('#userTablePage').trigger('click');
                $.post("/admin/edit", serialized)
                    .done(function (data) {
                        tr.getElementsByTagName("td")[1].innerHTML = data.name
                        tr.getElementsByTagName("td")[2].innerHTML = data.lastName
                        tr.getElementsByTagName("td")[3].innerHTML = data.age
                        tr.getElementsByTagName("td")[4].innerHTML = data.email
                        tr.getElementsByTagName("td")[5].innerHTML = data.userRoles
                        $('#userEditModal').trigger('reset');
                    })
            })
        });
};

function deleteUser(ths) {
    //удаление пользователя
    var tr = ths.parentNode.parentNode;
    var id = tr.getElementsByTagName("td")[0].innerHTML;
    $.get(`/admin/get/${id}`)
        .done(function (data) {
            $('#idD').attr('value', data.id)
            $('#nameD').attr('placeholder', data.name)
            $('#lastNameD').attr('placeholder', data.lastName)
            $('#ageD').attr('placeholder', data.age)
            $('#emailD').attr('placeholder', data.email)
            $('#modalDelete').modal('show');
            $('#delete_button').on('click', function () {
                var myform = $('#userDeleteModal');
                var disabled = myform.find(':input:disabled').removeAttr('disabled');
                disabled.attr('disabled', 'disabled');
                $('#modalDelete').modal('hide');
                $('#userTablePage').trigger('click');
                $.post("/admin/delete", {id: id})
                    .done(function () {
                        tr.remove();
                        myform.trigger('reset');
                    })
            });
        })
};

