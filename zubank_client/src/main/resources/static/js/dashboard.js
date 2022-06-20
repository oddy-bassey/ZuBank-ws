'use strict';

const customersCountURI = 'http://localhost:8080/api/v1/customers/count/';
const transactionsCountURI = 'http://localhost:8080/api/v1/transactions/count/';
const accountsURI = 'http://localhost:8080/api/v1/accountLookup/count/';

const makeRequest = (url) => {
    return axios.get(url)
        .then(({ data }) => data)
        .catch((error) => {
            console.log(error);
            return error;
        });
}

// load page data
const init = async () => {
    const statistics = await Promise.all([
        getTotalNumOfCustomers(), // returns a number
        getTotalNumOfTransactions(), // returns a number
        getTotalNumOfAccounts() // returns a number
    ]);
    renderPageStatistics(statistics);
}

// get total count of customers
const getTotalNumOfCustomers = () => makeRequest(customersCountURI);

// get total count of transactions
const getTotalNumOfTransactions = () => makeRequest(transactionsCountURI);

// get total count of accounts
const getTotalNumOfAccounts = () => makeRequest(accountsURI);

// display the count figures on the dashboard cards
const renderPageStatistics = (statistics) => {
    const [totalNumOfCustomers, totalNumOfTransactions, totalNumOfAccounts] = statistics;
    // set cards count values
    setElementText('total-customers', totalNumOfCustomers);
    setElementText('total-transactions', totalNumOfTransactions);
    setElementText('total-accounts', totalNumOfAccounts);
}

// set innerText of an element
const setElementText = (elemId, text) => {
    const element = document.querySelector(`#${elemId}`);
    element.innerText = `${text}`;
}

init();