//password matching
document.getElementById('confirmPassword').addEventListener('input', function () {
     var password = document.getElementById('password').value;
     var confirmPassword = document.getElementById('confirmPassword').value;
     var passwordErrorDiv = document.getElementById('passwordError');

     if (password !== confirmPassword) {
         passwordErrorDiv.style.display = 'block';
     } else {
         passwordErrorDiv.style.display = 'none';
     }
 });
 
 