<template>
  <div id="register" class="text-center form-container" @click="clearErrors">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="user.email" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              console.log('Registration successful, attempting login...');
              // Automatically log in the user after successful registration
              return authService.login({
                email: this.user.email,
                password: this.user.password
              });
            }
          })
          .then((loginResponse) => {
            console.log('Login response:', loginResponse);
            if (loginResponse && loginResponse.data && loginResponse.data.token) {
              // Set authentication token and user data
              this.$store.commit('SET_AUTH_TOKEN', loginResponse.data.token);
              this.$store.commit('SET_USER', loginResponse.data.user);
              this.$store.commit('INITIALIZE_USER');
              
              // Redirect to home page
              this.$router.push('/');
            } else {
              console.error('Login response missing token or data');
              this.registrationErrors = true;
              this.registrationErrorMsg = 'Registration successful but login failed. Please try logging in manually.';
            }
          })
          .catch((error) => {
            console.error('Registration or login error:', error);
            const response = error.response;
            this.registrationErrors = true;
            if (response && response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            } else if (response && response.status === 401) {
              this.registrationErrorMsg = 'Registration successful but automatic login failed. Please try logging in manually.';
            } else if (response && response.status === 409) {
              this.registrationErrorMsg = 'Username or email already exists.';
            } else {
              this.registrationErrorMsg = 'Registration or login failed. Please try again.';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
/* Align form fields left and inputs to the same right edge */
.form-input-group {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 1rem;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}
label {
  flex: 0 0 130px;
  text-align: left;
  margin-right: 1rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5rem;
  height: 1.5rem;
  display: flex;
  align-items: center;
}
input[type="text"],
input[type="email"],
input[type="password"] {
  flex: 1 1 0;
  min-width: 0;
  padding: 0.5rem;
  text-align: left;
  border-radius: 0;
  border-width: 1px 1px 0 1px;
  border-style: solid;
  border-color: #ccc;
  margin-bottom: -1px;
  background: #fff;
}
.form-input-group:first-of-type input {
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}
.form-input-group:last-of-type input {
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 6px;
  border-bottom-width: 1px;
}
.form-input-group:not(:last-of-type) input {
  border-bottom-width: 0;
}
button[type="submit"] {
  width: 100%;
  padding: 0.75rem;
  background-color: #1976d2;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  margin-top: 1rem;
  margin-bottom: 0.5rem;
  transition: background 0.2s;
}
button[type="submit"]:hover,
button[type="submit"]:focus {
  background-color: #1565c0;
}
h1 {
  margin-bottom: 10px;
  margin-top: 0px;
}
</style>
