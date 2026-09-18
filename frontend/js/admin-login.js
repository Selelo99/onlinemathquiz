
/* =========================================================
   ADMIN LOGIN
   ========================================================= */

const API_BASE_URL = "http://localhost:8080/api";


/* =========================================================
   ELEMENTS
   ========================================================= */

const adminForm = document.getElementById("admin-login-form");
const usernameInput = document.getElementById("admin-username");
const passwordInput = document.getElementById("admin-code");
const adminError = document.getElementById("admin-error");


/* =========================================================
   LOGIN
   ========================================================= */

adminForm.addEventListener("submit",
    async function (event) {

        event.preventDefault();

        /* -------------------------------------------------
           Clear previous error
        ------------------------------------------------- */

        adminError.textContent = "";


        /* -------------------------------------------------
           Get username and password
        ------------------------------------------------- */

        const username = usernameInput.value.trim();
        const password = passwordInput.value;


        /* -------------------------------------------------
           Validate username
        ------------------------------------------------- */

        if (!username) {

            adminError.textContent = "Please enter your admin username.";

            usernameInput.focus();

            return;
        }


        /* -------------------------------------------------
           Validate password
        ------------------------------------------------- */

        if (!password) {

            adminError.textContent = "Please enter your password.";

            passwordInput.focus();

            return;
        }


        /* -------------------------------------------------
           Disable button while logging in
        ------------------------------------------------- */

        const loginButton = adminForm.querySelector("button[type='submit']");

        loginButton.disabled = true;

        loginButton.textContent = "Signing in...";

        try {

            /* =============================================
               SEND LOGIN REQUEST
               ============================================= */

            const response =
                await fetch(`${API_BASE_URL}/auth/login`,
                    {
                        method: "POST",

                        credentials: "include",

                        headers: {
                            "Content-Type": "application/json",

                            "Accept": "application/json"
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
                        "Invalid JSON response:",
                        parseError
                    );

                    throw new Error(
                        "Server returned an invalid response."
                    );
                }
            }


            /* =============================================
               CHECK LOGIN
               ============================================= */

            if (
                !response.ok ||
                !data.success
            ) {

                throw new Error(
                    data.message ||
                    "Invalid username or password."
                );
            }


            /* =============================================
               LOGIN SUCCESS
               ============================================= */

            console.log(
                "Admin login successful:",
                data.username
            );


            /*
             * Frontend state.
             *
             * The real authentication is the
             * Spring Security HTTP session.
             */

            sessionStorage.setItem(
                "adminLoggedIn",
                "true"
            );


            sessionStorage.setItem(
                "adminUsername",
                data.username
            );


            /* =============================================
               GO TO ADMIN DASHBOARD
               ============================================= */

            window.location.href =
                "admin.html";

        }
        catch (error) {

            console.error(
                "Login error:",
                error
            );


            adminError.textContent =
                error.message ||
                "Unable to login. Please try again.";

        }
        finally {

            loginButton.disabled = false;

            loginButton.textContent =
                "Enter Admin Panel →";
        }

    }
);