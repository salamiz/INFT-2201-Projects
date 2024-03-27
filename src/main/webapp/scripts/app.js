/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 02/22/2024
 * Description: Client side javascript code to implement client side logic and functionalities dynamically
 *
 *    Copyright 2024 Zulkifli Salami
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */




/**
 * Dynamically creates and inserts the header into the page.
 */
function createHeader() {

    $.ajax({
        url: '/auth', // The URL to the servlet endpoint
        type: 'POST',
        data: {action: 'status'},
        success: function(response) {
            const isLoggedIn = response.loggedIn;
            const username = response.username;

            let authNavItem = isLoggedIn
                ? `<span class="navbar-text me-3">Welcome, ${username}</span><a class="nav-link" href="/logout"><i class="fa fa-sign-out-alt"></i> Logout</a>`
                : `<li class="nav-item"><a class="nav-link" href="../login.jsp"><i class="fa fa-sign-in"></i> Login</a></li>
                   <li class="nav-item"><a class="nav-link" href="../register.jsp"><i class="fa fa-user-plus me-1"></i> Sign Up</a></li>`;

            let headerHtml = `
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="../index.jsp">INFT2201 - Home</a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav ms-auto">
                                <li class="nav-item active"><a class="nav-link" href="../index.jsp"><i class="fa fa-home"></i> Home</a></li>
                                <li class="nav-item"><a class="nav-link" href="../about.jsp"><i class="fa fa-info-circle"></i> About Us</a></li>
                                ${authNavItem}
                            </ul>
                        </div>
                    </div>
                </nav>
            `;
            $('#navbar-placeholder').html(headerHtml);
        },
        dataType: 'json'
    });
}

/*
* Function to manage session state
* */
function checkSessionStateAndHandleInactivity() {
    let timeout;

    function resetTimer() {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            $.ajax({
                url: '/auth',
                type: 'POST',
                data: {action: 'checkSession'}, // Action parameter to check session status
                success: function(response) {
                    if (!response.sessionActive) {
                        alert("You have been logged out due to inactivity. Please log in again.");
                        window.location.href = "/login.jsp"; // Redirects to login page
                    } else {
                        // Session is still active, resets the timer
                        resetTimer();
                    }
                },
                error: function() {
                    console.log("Error checking session state.");
                },
                dataType: 'json'
            });
        }, 120000); // 2 minutes
    }

    // Reset the timer on user actions
    window.onload = resetTimer;
    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;
}

$(document).ready(function() {
    checkSessionStateAndHandleInactivity();
});



/**
 * Set background image for the homepage
 */
function setHomePageBackground() {
    $('#main-content').addClass('homepage-background');
}

/**
 * Add welcome text to the homepage
 */
function addWelcomeText() {
    let welcomeSection = $('<div>', { id: 'welcome-section' });
    let welcomeArticle = $('<article>', { id: 'welcome-article' });
    welcomeArticle.append($('<h1>').text('Welcome to Our Website!'));
    welcomeArticle.append($('<p>').text('This is our webpage for INFT2201 - Home.'));
    welcomeSection.append(welcomeArticle);
    $('#main-content').append(welcomeSection);
}


/**
 * Add fixed navar to the bottom of the page
 */
function addFixedNavbar() {
    // Creates the navbar container
    let navbar = document.createElement('nav');
    navbar.className = 'navbar navbar-expand-lg navbar-light bg-light fixed-bottom';

    // Creates the container inside the navbar
    let container = document.createElement('div');
    container.className = 'container-fluid';

    // Creates the copyright statement
    let currentDate = new Date().getFullYear();
    let copyright = document.createElement('span');
    copyright.textContent = `© ${currentDate} Zul`;

    // Appends elements together
    container.appendChild(copyright);
    navbar.appendChild(container);
    document.body.appendChild(navbar);
}


/**
 * Creates the contents of the About Us Page
 */
function createAboutUs() {
    let aboutContent = `
        <div class="container mt-5">
            <div class="row">
                <div class="col-lg-6">
                    <h2>About Me</h2>
                    <p>I am a seasoned software developer with years of experience in Full Stack Development.</p>
                    <a href="https://www.linkedin.com/in/zulkifli-salami/" class="btn btn-primary" target="_blank">Linkedln Profile</a>
                </div>
                <div class="col-lg-6">
                    <img src="/src/Zul.jpeg" alt="Zul" class="img-fluid rounded-circle">
                </div>
            </div>
        </div>
    `;

    // Append the about content to the main content area
    $('#main-content').html(aboutContent);
}


/**
 * Prepares the login page with necessary events
 */
function validateLoginPage() {
    $('#login-form').on('submit', function(event) {
        let errorMessages = [];

        // Get the user input from the input field
        let username = $('#username').val().trim();
        let password = $('#password').val();

        if (username === '') {
            errorMessages.push('Username cannot be empty.');
        } else if (username.length < 2 || username.length > 25){
            errorMessages.push('Username has to be between 2 and 25 characters long, Please Try Again.');
        }
        if (password === '') {
            errorMessages.push('Password cannot be empty.');
        }else if (password.length < 6 || password.length > 25){
            errorMessages.push('Password has to be between 6 and 25 characters long, Please Try Again.');
        }

        if (errorMessages.length > 0) {
            event.preventDefault(); // Prevent form submission for validation failures
            alert(errorMessages.join('\n')); // Display error messages
        }
    });
}

/*
* Function to display login errors and success messages
*/
function displayNotification() {
    const urlParams = new URLSearchParams(window.location.search);

    const success = urlParams.get('success');
    const error = urlParams.get('error');
    let notificationMessage = '';

    if(success){
        switch (success) {
            case 'successfulLogin':
                notificationMessage = "Congrats! You've been successfully logged in.";
                break;
            case 'successfulRegistration':
                notificationMessage = "Congrats! You've been successfully registered.";
                break;
        }
        alert(notificationMessage); // Display success message
    } else if (error) {
        switch (error) {
            case 'emptyFields':
                notificationMessage = 'Please fill in all fields.';
                break;
            case 'invalidCredentials':
                notificationMessage = 'Invalid password. Please try again.';
                break;
            case 'userNotFound':
                notificationMessage = 'Invalid Username. Please try again.';
                break;
            case 'validationFailed':
                notificationMessage = 'Invalid Input on registration, follow proper input guidelines. Please try again.';
                break;
            case 'usernameExists':
                notificationMessage = 'The username already exists. Please try again with a unique username.';
                break;
            case 'emailExists':
                notificationMessage = 'The email already exists. Please try again with a unique email.';
                break;
            case 'unexpectedError':
                notificationMessage = 'Unexpected error with executing registration operation. Please try again.';
                break;
            default:
                notificationMessage = 'An error occurred. Please try again.';
        }
        alert(notificationMessage); // Display error message
    }
}



// Function to prepare the Register Page functionality
function validateRegisterPage() {
    // Event listener for the register form submission
    $('#register-form').on('submit', function(event) {
        let errorMessages = [];

        let username = $('#username').val().trim();
        let firstName = $('#firstName').val().trim();
        let lastName = $('#lastName').val().trim();
        let email = $('#email').val().trim();
        let password = $('#password').val();
        let confirmPassword = $('#confirmPassword').val();

        // validate the user input
        if (username.length < 2 || username.length > 25){
            errorMessages.push('Username has to be between 2 and 25 characters long, Please Try Again.');
        }
        if (firstName.length < 2 || firstName.length > 25) {
            errorMessages.push('First Name must be between 2 to 25 characters long, Please Try Again.');
        }
        if (lastName.length < 2 || lastName.length > 25) {
            errorMessages.push('Last Name must be between 2 to 25 characters long, Please Try Again.');
        }
        if (email.length < 8 || email.length > 25 ) {
            errorMessages.push('Email must be between 8 and 25 characters long, Please Try Again.');
        } else if (!email.includes('@')){
            errorMessages.push('Email must contain an "@" symbol.');
        }
        if (password.length < 6 || password.length > 25) {
            errorMessages.push('Password must be between 6 to 25 characters long, Please Try Again.');
        }
        if (password !== confirmPassword) {
            errorMessages.push('Passwords must match.');
        }

        if (errorMessages.length > 0) {
            event.preventDefault(); // Prevent form submission for validation failures
            alert(errorMessages.join('\n')); // Display error messages
        }
    });
}


$(document).ready(function() {
    createHeader(); // Dynamically creates and inserts the header
    addFixedNavbar();

    $('body').on('click', '.navbar-toggler', function() {
        $('#navbarNav').collapse('toggle');
    });

    displayNotification();

    // Get the current page name from the URL
    const pageName = window.location.pathname.split("/").pop();

    // Conditional loading of functions based on the page name
    switch (pageName) {
        case "index.jsp":
            setHomePageBackground();
            addWelcomeText();
            break;
        case "about.jsp":
            // Function to dynamically create the services page, for example
            createAboutUs();
            break;
        case "login.jsp":
            // Function to implement login page functionality
            validateLoginPage();
            break;
        case "register.jsp":
            // Function to implement register page functionality
            validateRegisterPage();
            break;
        default:
            // Default case can be used for common functionality across all pages
            // or for handling unknown pages.
            console.log("This is a shared or unknown page.");
            break;
    }
});


