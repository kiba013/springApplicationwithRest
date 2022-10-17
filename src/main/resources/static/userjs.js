const url = "/api/user";

async function userById() {
        await fetch(url)
                .then((res) => res.json())
                        .then((user) => {
                            let data = "";
                            data +="<tr class='text-center'>"
                            data +="<td><p>" + user.id + "</p></td>";
                            data +="<td><p>" + user.username + "</p></td>";
                            data +="<td><p>" + user.email + "</p></td>";
                            data +="<td><p>"+ user.age + "</p></td>";
                            data +="<td><p>" + user.roleSet + "</p></td>";
                            data +="</tr>";
                            document.getElementById("user").innerHTML = data;
                        });
}
userById();

