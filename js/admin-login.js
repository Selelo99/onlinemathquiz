/* =========================================================
   ADMIN LOGIN
   ========================================================= */

const API_BASE_URL =
    "https://onlinemathquiz-production.up.railway.app/api";


/* =========================================================
   ELEMENTS
   ========================================================= */

const adminForm =
    document.getElementById("admin-login-form");

const usernameInput =
    document.getElementById("admin-username");

const passwordInput =
    document.getElementById("admin-code");

const adminError =
    document.getElementById("admin-error");


/* =========================================================
   CHECK ELEMENTS
   ========================================================= */

if (!adminForm) {

    console.error(
        "Admin login form was not found."
    );

}


/* =========================================================
   LOGIN
   ========================================================= */

adminForm.addEventListener(
    "submit",

    async function (event) {

        event.preventDefault();


        /* -------------------------------------------------
           CLEAR ERROR
        ------------------------------------------------- */

        adminError.textContent = "";


        /* -------------------------------------------------
           GET FORM VALUES
        ------------------------------------------------- */

        const username =
            usernameInput.value.trim();

        const password =
            passwordInput.value;


        /* -------------------------------------------------
           VALIDATE USERNAME
        ------------------------------------------------- */

        if (!username) {

            adminError.textContent =
                "Please enter your admin username.";

            usernameInput.focus();

            return;
        }


        /* -------------------------------------------------
           VALIDATE PASSWORD
        ------------------------------------------------- */

        if (!password) {

            adminError.textContent =
                "Please enter your password.";

            passwordInput.focus();

            return;
        }


        /* -------------------------------------------------
           LOGIN BUTTON
        ------------------------------------------------- */

        const loginButton =
            adminForm.querySelector(
                "button[type='submit']"
            );


        loginButton.disabled = true;

        loginButton.textContent =
            "Signing in...";


        try {


            /* =============================================
               LOGIN REQUEST
            ============================================= */

            const response =

                await fetch(
                    `${API_BASE_URL}/auth/login`,

                    {

                        method: "POST",

                        credentials: "include",

                        headers: {

                            "Content-Type":
                                "application/json",

                            "Accept":
                                "application/json"
                        },

                        body: JSON.stringify({

                            username: username,

                            password: password
                        })
                    }
                );


            /* =============================================
               READ RESPONSE
            ============================================= */

            const text =
                await response.text();


            console.log(
                "Login HTTP status:",
                response.status
            );


            console.log(
                "Login response:",
                text
            );


            let data = {};


            if (text) {

                try {

                    data =
                        JSON.parse(text);

                }
                catch (parseError) {

                    console.error(
                        "Invalid JSON:",
                        parseError
                    );

                    throw new Error(
                        "Server returned an invalid response."
                    );
                }
            }


            /* =============================================
               CHECK RESPONSE
            ============================================= */

            if (!response.ok) {

                throw new Error(

                    data.message ||

                    `Login failed. HTTP ${response.status}.`
                );
            }


            if (!data.success) {

                throw new Error(

                    data.message ||

                    "Login failed."
                );
            }


            /* =============================================
               LOGIN SUCCESS
            ============================================= */

            console.log(
                "Admin login successful:",
                data.username
            );


            sessionStorage.setItem(
                "adminLoggedIn",
                "true"
            );


            sessionStorage.setItem(
                "adminUsername",
                data.username
            );


            /* =============================================
               REDIRECT
            ============================================= */

            window.location.href =
                "admin.html";
        }


        catch (error) {


            console.error(
                "Login error:",
                error
            );


            /*
             * fetch() throws TypeError: Failed to fetch
             * when the browser cannot complete the request,
             * commonly because of CORS/network problems.
             */

            if (
                error instanceof TypeError &&
                error.message === "Failed to fetch"
            ) {

                adminError.textContent =
                    "Unable to connect to the login server. Please try again.";

            }
            else {

                adminError.textContent =
                    error.message ||
                    "Unable to login. Please try again.";
            }
        }


        finally {

            loginButton.disabled = false;

            loginButton.textContent =
                "Enter Admin Panel →";
        }

    }
);