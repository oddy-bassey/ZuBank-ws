'use strict';

const account_queryURI = 'http://localhost:8080/api/v1/accountLookup';

// extract accountId from page URL
const extractParams = () => {
  const search = window.location.search;
  const queryList = search.slice(1).split('&');

  if (queryList.toString() === '') {
    return null;
  }

  return queryList.reduce((result, item) => {
    const [key, value] = item.split('=');
    result[key] = value;
    return result;
  }, {});
};

// make request using the accountId to fetch account details
const getAccount = (accountId) => {
  return axios
    .get(`${account_queryURI}/details/${accountId}`)
    .then(({ data }) => data)
    .catch((error) =>
      swal('Request Error', "Unable to get account details", 'error')
    );
};

/* load page data */
const init = async () => {
  const { account: accountId } = extractParams();
  console.log(accountId);
  const {accountInfo, accountTransactions, customerInfo} = await getAccount(accountId);
  console.log(accountInfo, accountTransactions, customerInfo);
  displayCustomerInfo(customerInfo)
  displayAccBasicInfo(accountInfo);
  displayTransactionsData(accountTransactions);
};

const displayCustomerInfo = ({ id, firstName, lastName, dateOfBirth, email }) => {
  const customerDetailDiv = document.querySelector('#customer-detail');
  customerDetailDiv.innerHTML = `
    <p><strong>Customer ID:</strong> ${id}</p>
    <p><strong>First Name:</strong> ${firstName}</p>
    <p><strong>Last Name:</strong> ${lastName}</p>
    <p><strong>Date of Birth:</strong> ${dateOfBirth}</p>
    <p><strong>Email:</strong> ${email}</p>
  `;
};

const displayAccBasicInfo = ({id, name, accountType, balance, createdDate}) => {
  const accDetailDiv = document.querySelector('#account-detail');
  accDetailDiv.innerHTML = `
    <p><strong>Account ID:</strong> ${id}</p>
    <p><strong>Account Name:</strong> ${name}</p>
    <p><strong>Account Type:</strong> ${accountType}</p>
    <p><strong>Total Balance:</strong> ${balance}</p>
    <p><strong>Creation Date:</strong> ${new Date(createdDate).toLocaleString()}</p>
  `;
};

// render account's transactions in the table
const displayTransactionsData = (data) => {
  const tableBody = document.querySelector('#table-body');
  const tableRows = data.map(
    ({ id, accountId, transactionType, amount, transactionTime }, index) => `
    <tr>
      <th scope="row">${index + 1}</th>
      <td data-bs-toggle="tooltip" data-bs-placement="left" title=${id}>
        ${id.slice(0, 14) + '***'}
      </td>
      <td>${accountId}</td>
      <td>${transactionType}</td>
      <td>${amount}</td>
      <td>${new Date(transactionTime).toLocaleString()}</td>
    </tr>
  `
  );

  // populate table with tableRows
  tableBody.innerHTML = tableRows.join('');
};

init();
