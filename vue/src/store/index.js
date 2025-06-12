import { createStore as _createStore } from 'vuex';
import axios from 'axios';
import WordService from '../services/WordService';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      vocabList: [],
      vocabListLoaded: false
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      INITIALIZE_USER(state) {
        WordService.getWordsByUserId()
          .then(response => {
            state.vocabList = response.data;
            state.vocabList.sort((a, b) => a.word.localeCompare(b.word));
            state.vocabListLoaded = true;
          })
          .catch(error => {
            console.error('Error fetching user words:', error);
          });
      },
      ADD_WORD(state, word) {
        state.vocabList.push(word);
        state.vocabList.sort((a, b) => a.word.localeCompare(b.word));
      },
      UPDATE_WORD(state, updatedWord) {
        const index = state.vocabList.findIndex(word => word.id === updatedWord.id);
        if (index !== -1) {
          state.vocabList.splice(index, 1, updatedWord);
        }
      },
      DELETE_WORD(state, id) {
        state.vocabList = state.vocabList.filter(word => word.wordId !== id);
        console.log(state.vocabList)
      },
    },
  });
  return store;
}
