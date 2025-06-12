import axios from 'axios';

export default {
  createWord(word) {
    return axios.post('/word', word);
  },
  getWord(id) {
    return axios.get(`/word/${id}`);
  },
  updateWord(word) {
    return axios.put('/word', word);
  },
  deleteWord(id) {
    return axios.delete(`/word/${id}`);
  },
  getWordsByUserId() {
    return axios.get('/word/user');
  }
}