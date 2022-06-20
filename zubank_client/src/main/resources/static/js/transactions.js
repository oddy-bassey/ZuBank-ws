'use strict';

const tableBody = document.querySelector('#table-body');

const transactionsURI = 'http://localhost:8080/api/v1/transactions';

const extractErrorMssg = (error) => error.response.data.message;

/* load page data */
const init = async () => {
  // make request to fetch list of all transactions
  await axios
    .get(`${transactionsURI}/`)
    .then(({ data }) => {
      console.log('Success:', data);
      // load the table UI
      if (data && data.length > 0) {
        loadTableData(data);
      }
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Connection error', errMssg, 'error');
    });
};

const loadTableData = (data) => {
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
