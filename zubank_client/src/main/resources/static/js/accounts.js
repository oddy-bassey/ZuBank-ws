'use strict';

const modalTitle = document.querySelector('.modal-title');
const modalBody = document.querySelector('.modal-body');
const tableBody = document.querySelector('#table-body');
const totalAccounts = document.querySelector('#total-accounts');
let accounts = null;
let selectedAccId = '';

const account_cmdURI = 'http://localhost:8080/api/v1/accounts';
const account_queryURI = 'http://localhost:8080/api/v1/accountLookup';

const extractErrorMssg = (error) => error.response.data.message;
const setRequestOptions = (method, data) => ({
  method,
  headers: {
    'Content-Type': 'application/json',
  },
  data: JSON.stringify(data),
});

console.dir(axios);

/* load page data */
const init = async () => {
  await axios
    .get(`${account_queryURI}/`)
    .then(({ data }) => {
      console.log('Success:', data);
      accounts = data.accounts;
      // load the table UI
      if (accounts && accounts.length > 0) {
        loadTableData(accounts);
        initializeEventListeners();
      }
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Connection error', errMssg, 'error');
    });
};

const handleDelete = (id) => {
  console.log('DELETED ACCOUNT: ', id);
  // make request to backend server to delete acc using the id
  axios
    .delete(`${account_cmdURI}/closeAccount/${id}`)
    .then(({ data }) => {
      console.log('Success:', data);
      swal('Success!', data.message, 'success').then(() => init());
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Error!', 'Unable to delete account', 'error');
    });
};

// sends request for deposit and withdraw transaction
const sendTransaction = (url, formData) => {
  // make request to backend server
  axios(url, setRequestOptions('PUT', formData))
    .then(({ data }) => {
      console.log('Success:', data);
      swal('Transaction Successful!', data.message, 'success').then(() => {
        init();
        // close modal
        document.querySelector('.btn-close').click();
      });
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Transaction Failed', errMssg, 'error');
    });
};

// sends request for funds transfer
const transferFund = (formData) => {
  // make request to backend server
  axios(
    `${account_cmdURI}/transferFunds/`,
    setRequestOptions('POST', formData)
  )
    .then(({ data }) => {
      console.log('Success:', data);
      swal('Transaction Successful!', data.message, 'success').then(() => {
        init();
        // close modal
        document.querySelector('.btn-close').click();
      });
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Transaction Failed', errMssg, 'error');
    });
};

const loadTableData = (data) => {
  totalAccounts.innerText = data.length;
  const tableRows = data.map(
    // TODO: remember to destructure accountName
    ({
      id: accId,
      name,
      accountType,
      balance,
      createdDate,
      customerId,
    }) => `
      <tr class="table-item" id="${accId}">
        <td data-bs-toggle="tooltip" data-bs-placement="left" title=${customerId}>
          ${customerId.slice(0, 14) + '***'}
        </td>
        <td>${accId}</td>
        <td>${name}</td>
        <td>${accountType}</td>
        <td>${balance}</td>
        <td>${new Date(createdDate).toLocaleString()}</td>
        <td>
          <div class="dropdown">
            <a
              href="#"
              role="button"
              id="dropdownMenuLink"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <i class="bi bi-three-dots-vertical"></i>
            </a>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
              <li class="view-details">
                <a class="dropdown-item" href="/accounts/detail?accountId=${accId}">
                  View details
                </a>
              </li>
              <li class="dropdown-item deposit">Deposit</li>
              <li class="dropdown-item withdraw">Withdraw</li>
              <li class="dropdown-item transfer">Transfer</li>
              <li class="dropdown-item delete">Delete</li>
            </ul>
          </div>
        </td>
      </tr>
    `
  );

  // populate table with tableRows
  tableBody.innerHTML = tableRows.join('');
};

// add event listeners
const initializeEventListeners = () => {
  const tableItems = document.querySelectorAll('.table-item');
  const depositOptions = document.querySelectorAll('.deposit');
  const withdrawOptions = document.querySelectorAll('.withdraw');
  const transferOptions = document.querySelectorAll('.transfer');

  // row menu and delete menu item event handled
  for (const item of tableItems) {
    handleTableRowEvent(item);
    handleDeleteEvent(item);
  }

  // deposit form event
  setModalDisplayEvent(depositOptions, () =>
    renderTransactionForm(
      'Deposit Funds',
      'Deposit',
      `${account_cmdURI}/depositFunds/${selectedAccId}`
    )
  );

  // withdraw form event
  setModalDisplayEvent(withdrawOptions, () =>
    renderTransactionForm(
      'Withdraw Funds',
      'Withdraw',
      `${account_cmdURI}/withdrawFunds/${selectedAccId}`
    )
  );

  // transfer fund form event
  setModalDisplayEvent(transferOptions, () =>
    renderTransferForm('Transfer Funds')
  );
};

// row menu event handler
const handleTableRowEvent = (rowMenu) => {
  rowMenu.addEventListener('click', function () {
    selectedAccId = this.id;
  });
};

// delete menu event handler
const handleDeleteEvent = (deleteMenuItem) => {
  deleteMenuItem
    .querySelector('.delete')
    .addEventListener('click', function () {
      handleDelete(selectedAccId);
    });
};

// funds transaction event handler
const handleTransactionEvent = (form, submitForm) => {
  const amountInput = document.querySelector('#amount');
  if (form) {
    form.addEventListener(
      'submit',
      function (e) {
        e.preventDefault();
        const data = new FormData(e.target);
        const formData = Object.fromEntries(data.entries());
        // show validation message
        // when input is empty or amount is less than 10
        if (Number(formData.amount) < 10) {
          amountInput.classList.add('is-invalid');
          return;
        }
        // submit form
        console.log(formData);
        submitForm(formData);
        amountInput.classList.remove('is-invalid');
      },
      false
    );
  }
};

// handle modal display events
const setModalDisplayEvent = (options, renderContentFn) => {
  for (const option of options) {
    option.dataset.bsToggle = 'modal';
    option.dataset.bsTarget = '#modal';
    option.addEventListener('click', function () {
      clearModalPrevContent();
      renderContentFn();
    });
  }
};

// clears modal previous content
const clearModalPrevContent = () => {
  modalTitle.innerText = '';
  modalBody.innerHTML = '';
};

/* Deposit and Withdrawal Form */
const renderTransactionForm = (title, action, actionURL = '') => {
  console.log(selectedAccId);
  const { id, accountName = 'Megan Smith' } = accounts.find(
    (acc) => acc.id === selectedAccId
  );
  modalTitle.innerText = title;
  modalBody.innerHTML = `
      <form class="row g-3 transaction-form needs-validation" novalidate>
          <div class="col-md-6">
              <label for="accountId" class="col-form-label">AccountId:</label>
              <input name="accountId" value="${id}" type="text" class="form-control form-control-sm" id="accountId" readonly>
          </div>
          <div class="col-md-6">
              <label for="accountName" class="col-form-label">Account Name:</label>
              <input name="accountName" value="${accountName}" type="text" class="form-control form-control-sm" id="accountName" readonly>
          </div>
          <div class="col-12">
              <label for="amount" class="col-form-label">Amount:</label>
              <input name="amount" type="number" class="form-control form-control-sm" id="amount" aria-describedby="amountFeedback" required>
              <div id="amountFeedback" class="invalid-feedback">
                Amount should not be less than $10
              </div>
          </div>
          <div class="col-12">
            <button type="submit" class="btn btn-sm btn-primary">${action}</button>
          </div>
      </form>
  `;
  const transactionForm = document.querySelector('.transaction-form');
  handleTransactionEvent(transactionForm, (formData) =>
    sendTransaction(actionURL, formData)
  );
};

/* transfer funds Form */
const renderTransferForm = (title) => {
  console.log(selectedAccId);
  const { id } = accounts.find((acc) => acc.id === selectedAccId);
  // renders the select options of receivers accId
  const renderReceiversAccList = () => {
    const receiversAccList = accounts.filter(({ id }) => id !== selectedAccId);
    const selectOptions = receiversAccList.map(
      ({ id }) => `<option value="${id}">${id}</option>`
    );
    return selectOptions.join('');
  };

  modalTitle.innerText = title;
  modalBody.innerHTML = `
      <form class="row g-3 transfer-form needs-validation" novalidate>
          <div class="col-md-6">
              <label for="senderAccountId" class="col-form-label">Sender AccountId:</label>
              <input name="senderAccountId" value="${id}" type="text" class="form-control form-control-sm" id="senderAccountId" readonly>
          </div>
          <div class="col-md-6">
              <label for="receiverAccountId" class="col-form-label">Receiver AccountId:</label>
              <select name="receiverAccountId" id="receiverAccountId" class="form-select form-select-sm" aria-label="Receiver AccountId" required>
                ${renderReceiversAccList()}
              </select>
          </div>
          <div class="col-12">
              <label for="amount" class="col-form-label">Amount:</label>
              <input name="amount" type="number" class="form-control form-control-sm" id="amount" aria-describedby="amountFeedback" required>
              <div id="amountFeedback" class="invalid-feedback">
                Amount should not be less than $10
              </div>
          </div>
          <div class="col-12">
            <button type="submit" class="btn btn-sm btn-primary">Transfer</button>
          </div>
      </form>
  `;
  const transferForm = document.querySelector('.transfer-form');

  handleTransactionEvent(transferForm, transferFund);
};

init();
