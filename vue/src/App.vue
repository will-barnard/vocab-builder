<template>
  <div id="vocab-builder">
    <TopBanner />

    <router-view />
    <footer  class="app-footer">
      <span v-if="$route.name !== 'about'" @click="$router.push({name: 'about'})" class="footer-link">About</span>
      <span v-if="$route.name !== 'about'" class="footer-separator">|</span>
      <template v-if="$store.state.token !== ''">
        <span @click="$router.push({name: 'logout'})" class="footer-link">Logout</span>
        <span v-if="$store.state.token !== ''" class="footer-separator">|</span>
      </template>
      <span @click="goHome" class="footer-link" title="Home">
        <i class="fa fa-home"></i>
      </span>
    </footer>
  </div>
</template>

<script>
import TopBanner from "./components/TopBanner.vue";

export default {
  components: {
    TopBanner
  },
  created() {
    if (this.$store.state.token) {
      this.$store.commit('INITIALIZE_USER');
    }
  }
  ,
  methods: {
    goHome() {
      // Always route to home, even if already there
      if (this.$route.name === 'home') {
        this.$router.replace({ name: 'home' });
      } else {
        this.$router.push({ name: 'home' });
      }
    }
  }

};
</script>

<style>
:root {
  --primary-color: #5cc0b7;
  --secondary-color: #35495e;
  --tertiary-color: #69a6d4;
  --text-color: #2c3e50;

  --background-color: #c5eeff;
  --form-background-color: #ffffff;
  --form-border-color: #e0e0e0;
  --form-input-color: #333333;
  --form-input-border-color: #cccccc;
  --form-input-focus-color: #42b983;
  --form-button-color: #42b983;

  --link-background-color: #527aff;

  background-color: var(--background-color);
  color: var(--text-color);

  font-family: Arial, sans-serif;


}
.form-container {
  background-color: var(--form-background-color);
  border: 1px solid var(--form-border-color);
  padding: 20px;
  border-radius: 5px;
}
.generic-buttons p {
  margin: 10px 0;
  color: white;
  cursor: pointer;
  background-color: var(--secondary-color);
  padding: 10px;
  border-radius: 5px;;
}
#vocab-builder {
  padding-top: 20px; /* Reduced padding since no fixed nav */
}
.meaning {
    color: white;
    background-color: var(--secondary-color);
    padding: 10px;
    margin-bottom: 10px;
    border-radius: 5px;
}
.spacer {
  flex-grow: 1;
}
.single-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.app-footer {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center; /* Center align content */
  color: var(--text-color, #333);
  font-size: 1rem;
  background: var(--form-background-color, #fff);
  padding: 18px 0; /* Remove left padding */
  box-shadow: 0 -2px 8px rgba(0,0,0,0.04);
  z-index: 1001;
}
.footer-link {
  cursor: pointer;
  color: var(--primary-color, #007bff);
  text-decoration: underline;
  margin: 0 5px;
}
.footer-link:hover {
  text-decoration: none;
}
.footer-separator {
  margin: 0 8px;
  color: var(--form-border-color, #ccc);
}
.footer-link .fa-home {
  font-size: 1.1em;
  vertical-align: middle;
}
</style>