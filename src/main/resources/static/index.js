const url = "/admin/api/users";

// GET All Users
async function getAllUser() {
    await fetch(url)
        .then((res) => res.json())
        .then((response) => {
            let tempData = "";
            response.forEach((user) => {
                tempData +="<tr class='text-center'>"
                tempData +="<td><p>" + user.id + "</p></td>";
                tempData +="<td><p>" + user.username + "</p></td>";
                tempData +="<td><p>" + user.email + "</p></td>";
                tempData +="<td><p>"+ user.age + "</p></td>";
                tempData +="<td><p>" + user.roleSet + "</p></td>";
                tempData +="<td><a type='button' role='button' data-bs-target='#myModal' data-bs-toggle='modal' onclick='updateUserCall(" + user.id + ")' class='d-inline-block m-2 btn btn-outline-primary'>Update</a>" +
                    "<button onclick='deleteUser(" + user.id + ")' class='btn btn-outline-danger'>Delete</button></td>";
                tempData +="</tr>";
            })
            document.getElementById("users-list").innerHTML = tempData;
        })
}
getAllUser();

// Clear Form
function clearForm() {
    document.getElementById("username").value = "";
    document.getElementById("age").value = "";
    document.getElementById("email").value = "";
    document.getElementById("password").value = "";
    document.querySelectorAll('#roles select').value = "";
}


// GET all Roles from DB
async function getAllRoles() {
    await fetch("/roles")
        .then((res) => res.json())
        .then((response) => {
            let check = "";
            response.forEach((role) => {
                check +="<option value='" + role.id + "'> " + role.name + "</option>"
            });
            document.getElementById("roles").innerHTML = check;
            document.getElementById("updateRole").innerHTML = check;
        })
}
getAllRoles();

// Create new User
async function addNewUser() {
    const username = document.getElementById("username");
    const age = document.getElementById("age");
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const roles = document.querySelectorAll('#roles option');
    let arrRole = [];
    function getRole() {
        arrRole = [];
        roles.forEach(item => {
            if (item.selected) {
                let data = {
                    id: item.value
                }
                arrRole.push(data);
            }
        })
    }
    getRole();
    let user = {
        username: username.value,
        age: age.value,
        email: email.value,
        password: password.value,
        role: arrRole
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    if (response.ok) {
        alert("User successfully created");
    }
    else {
        alert(response.status);
    }
    await getAllUser();
    await clearForm()
}

// Submit from button
document.getElementById("submitBtn").addEventListener('click', async (e) =>{
    e.preventDefault();
    await addNewUser();
})

// Delete User from DB
async function deleteUser(id) {
    const response = await
        fetch(`${url}/${id}`, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json'
            }
        });
    if (response.ok) {
        alert("User with ID: " + id + " deleted successfully")
    }   else {
        alert("Error HTTP" + response.status);
    }
    await getAllUser();
}

// UPDATE current User
async function setForm(username, password, age, email, roles) {
    document.getElementById("updateUsername").value = username;
    document.getElementById("updatePassword").value = password;
    document.getElementById("updateAge").value = age;
    document.getElementById("updateEmail").value = email;
    document.querySelectorAll("#updateRole option").value = email;
}
let editData;
async function updateUserCall(id) {
    await fetch(`${url}/${id}`)
        .then((res) => res.json()
            .then((response) => {
                editData = response;
                setForm(editData.username, editData.password, editData.age, editData.email, editData.role);
            }))
}

function getFormData() {
    const roles = document.querySelectorAll('#updateRole option');
    let arrRole = [];
    function getRole() {
        arrRole = [];
        roles.forEach(item => {
            if (item.selected) {
                let data = {
                    id: item.value
                }
                arrRole.push(data);
            }
        })
    }
    getRole();
    return {
        username:document.getElementById("updateUsername").value,
        password:document.getElementById("updatePassword").value,
        age:document.getElementById("updateAge").value,
        email:document.getElementById("updateEmail").value,
        role: arrRole
    }
}

async function updateUser() {
    const formData = getFormData();
    formData['id'] = editData.id;
    const data = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    clearForm();
    await getAllUser();

}
document.getElementById("updateBtn").addEventListener('click', async (e) =>{
    e.preventDefault();
    await updateUser();
});

// async function getRoleForOneUser() {
//     await fetch("/roles")
//         .then((res) => res.json())
//         .then((response) => {
//             let check = "";
//             response.forEach((role) => {
//                 check +="<button class='dropdown-item' type='button'>"
//                 check +="<input  id='" + role.id + "' value='" + role.id + "' type='checkbox' required>"
//                 check +="<label for='" + role.id + "'>" + role.name + "</label>"
//                 check +="</button>"
//             })
//             document.getElementById("updateRole").innerHTML = check;
//         })
// }
// getRoleForOneUser();

