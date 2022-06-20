'use strict';

/* fetch customers data frm server */
const modalTitle = document.querySelector('.modal-title');
const modalBody = document.querySelector('.modal-body');
const tableBody = document.querySelector('#table-body');
const createCustomerBtn = document.querySelector('#create-customer-btn');
const totalCustomers = document.querySelector('#total-customers');
let selectedCustomerId = '';

const customersURI = 'http://localhost:8080/api/v1/customers';
const account_cmdURI = 'http://localhost:8080/api/v1/accounts';

const extractErrorMssg = (error) => error.response.data.message;
const setRequestOptions = (method, url, data) => ({
  method,
  url,
  data: JSON.stringify(data),
  headers: {
    'Content-Type': 'application/json',
  },
});

/* load page data */
const init = async () => {
  await axios
    .get(`${customersURI}/`)
    .then((response) => {
      const { data } = response;
      console.log('Success:', data);
      // load the table UI
      if (data && data.length > 0) {
        loadTableData(data);
        initializeEventListeners();
      }
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Connection error', errMssg, 'error');
    });
};

// delete customer request
const handleDelete = (id) => {
  console.log('DELETED CUSTOMER: ', id);
  // make request to backend server to delete customer using the id
  axios
    .delete(`${customersURI}/${id}`)
    .then((response) => {
      console.log('Success:', response.data);
      swal('Deleted!', response.data, 'success').then(() => init());
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Error!', 'Unable to delete customer', 'error');
    });
};

// create account request
const handleCreateAccFormSubmit = (formData) => {
  // make request to backend server
  // actionURL is the 'backend-url' of the customers endpoint
  axios(setRequestOptions('POST', `${account_cmdURI}/openAccount/`, formData))
    .then(({ data }) => {
      console.log('Success:', data);
      swal('Success!', data.message, 'success').then(() => {
        init();
        // close modal
        document.querySelector('.btn-close').click();
      });
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Unable to Create Account', errMssg, 'error');
    });
};

/* Create and Edit customer request */
const handleCustomerFormSubmit = (url, method, formData) => {
  // make request to backend server
  axios(setRequestOptions(method, url, formData))
    .then(({ data }) => {
      console.log('Success:', data);
      swal('Successful!', data, 'success').then(() => {
        init();
        // close modal
        document.querySelector('.btn-close').click();
      });
    })
    .catch((error) => {
      const errMssg = extractErrorMssg(error);
      console.error('Error:', errMssg);
      swal('Error!', errMssg, 'error');
    });
};

const createCustomer = (formData) => {
  handleCustomerFormSubmit(`${customersURI}/`, 'POST', formData);
};

const updateCustomer = (formData, id) => {
  handleCustomerFormSubmit(`${customersURI}/${id}`, 'PUT', formData);
};

const getCustomer = (customerId) => {
  return axios
    .get(`${customersURI}/${customerId}`)
    .then(({ data }) => data)
    .catch((error) =>
      swal('Request Error', "Unable to get customer's data", 'error')
    );
};

const loadTableData = (data) => {
  totalCustomers.innerText = data.length;

  const tableRows = data.map(
    ({ id, firstName, lastName, dateOfBirth, email, gender}, index) => `
        <tr class="table-item" id="${id}">
          <th scope="row">${index + 1}</th>
          <td>${firstName}</td>
          <td>${lastName}</td>
          <td>${gender}</td>
          <td>${dateOfBirth}</td>
          <td>${email}</td>
          <td>
            <div class="dropdown">
              <a
                class=""
                href="#"
                role="button"
                id="dropdownMenuLink"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <i class="bi bi-three-dots-vertical"></i>
              </a>
              <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <li class="dropdown-item edit">Edit</li>
                <li class="dropdown-item view-details">View details</li>
                <li class="dropdown-item create-acc">Create account</li>
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
  const editOptions = document.querySelectorAll('.edit');
  const customerDetailsOptions = document.querySelectorAll('.view-details');
  const createAccOptions = document.querySelectorAll('.create-acc');

  // row menu and delete menu item event handled
  for (const item of tableItems) {
    handleTableRowEvent(item);
    handleDeleteEvent(item);
  }

  // edit event
  setModalDisplayEvent(editOptions, () =>
    renderCustomerForm('Update Customer', '/api/customer', 'edit')
  );

  // view details event
  setModalDisplayEvent(customerDetailsOptions, renderCustomerDetails);

  // create acc event
  setModalDisplayEvent(createAccOptions, () =>
    renderCreateAccForm('Create Account', '/api/withdraw')
  );
};

// create new customer event handler
const handleNewCustomerEvent = () => {
  createCustomerBtn.addEventListener('click', function () {
    clearModalPrevContent();
    renderCustomerForm('New Customer', '/api/customer');
  });
};

// create new customer event
handleNewCustomerEvent();

// row menu event handler
const handleTableRowEvent = (rowMenu) => {
  rowMenu.addEventListener('click', function () {
    selectedCustomerId = this.id;
  });
};

// delete menu event handler
const handleDeleteEvent = (deleteMenuItem) => {
  deleteMenuItem
    .querySelector('.delete')
    .addEventListener('click', function () {
      handleDelete(selectedCustomerId);
    });
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

/* Create and Edit Customer Form */
const renderCustomerForm = async (title, actionURL = '', actionType) => {
  // console.log(selectedCustomerId);
  let customer = null;
  if (selectedCustomerId && actionType === 'edit') {
    // make request to endpoint (getCustomerById) using the customer
    customer = await getCustomer(selectedCustomerId);
    console.log(customer);
  }

  modalTitle.innerText = title;
  modalBody.innerHTML = `
      <form class="row g-3 customer-form needs-validation" novalidate>
          <div class="col-md-6">
              <label for="firstName" class="col-form-label">First Name:</label>
              <input name="firstName" type="text" value="${
                customer?.firstName || ''
              }" class="form-control form-control-sm" id="firstName" required>
          </div>
          <div class="col-md-6">
              <label for="lastName" class="col-form-label">Last Name:</label>
              <input name="lastName" type="text" value="${
                customer?.lastName || ''
              }" class="form-control form-control-sm" id="lastName" required>
          </div>
          <div class="col-md-6">
              <label for="gender" class="col-form-label">Gender:</label>
              <select name="gender" id="gender" class="form-select form-select-sm" aria-label="Gender">
                <option value="male" ${customer?.gender === 'male' && 'selected'}>Male</option>
                <option value="female" ${customer?.gender === 'female'  && 'selected'}>Female</option>
              </select>
          </div>
          <div class="col-md-6">
              <label for="dateOfBirth" class="col-form-label">Date of Birth:</label>
              <input name="dateOfBirth" type="date" value="${
                customer?.dateOfBirth || ''
              }" class="form-control form-control-sm" id="dateOfBirth" required>
          </div>
          <div class="col-12">
              <label for="email" class="col-form-label">Email:</label>
              <input name="email" type="email" value="${
                customer?.email || ''
              }" class="form-control form-control-sm" id="email" required>
          </div>
          <div class="col-12">
            <button type="submit" class="btn btn-sm btn-primary">Submit</button>
          </div>
      </form>
    `;

  const customerForm = document.querySelector('.customer-form');
  customerForm.addEventListener(
    'submit',
    function (e) {
      e.preventDefault();
      // show validation message
      if (!this.checkValidity()) {
        this.classList.add('was-validated');
        return;
      }
      // submit form
      const data = new FormData(e.target);
      const formData = Object.fromEntries(data.entries());
      if (actionType === 'edit') {
        console.log(formData);
        updateCustomer(formData, selectedCustomerId);
      } else {
        console.log(formData);
        createCustomer(formData);
      }
    },
    false
  );
};

/* Customer Details */
const renderCustomerDetails = async () => {
  console.log(selectedCustomerId);
  // make request to endpoint (getCustomerById) using the customer
  const customer = await getCustomer(selectedCustomerId);
  const { firstName, lastName, email, dateOfBirth, gender } = customer;
  console.log(customer);

  modalTitle.innerText = 'Customer Details';
  // replace hardcoded values with data from retrieved from the server
  modalBody.innerHTML = `
      <div class="customer-details">
          <p>Name: ${firstName} ${lastName}</p>
          <p>Gender: ${gender}</p>
          <p>Email: ${email}</p>
          <p>DOB: ${dateOfBirth}</p>
      </div>
  `;
};

/* Create Account Form */
const renderCreateAccForm = async (title, actionURL = '') => {
  console.log(selectedCustomerId);
  modalTitle.innerText = title;
  modalBody.innerHTML = `
        <form class="account-form needs-validation" novalidate>
            <div class="mb-3">
                <label for="accType" class="col-form-label">Account Type:</label>
                <select name="accountType" id="accType" class="form-select form-select-sm" aria-label="Account_type">
                    <option value="SAVINGS">Savings</option>
                    <option value="CURRENT">Current</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="initialAmount" class="col-form-label">Initial Amount:</label>
                <input name="initialCredit" type="number" value="0" class="form-control form-control-sm" id="initialAmount" required>
            </div>
            <div class="col-12">
              <button type="submit" class="btn btn-sm btn-primary">Create</button>
            </div>
        </form>
    `;

  const accountForm = document.querySelector('.account-form');
  accountForm.addEventListener('submit', function (e) {
    e.preventDefault();
    // show validation message
    if (!this.checkValidity()) {
      this.classList.add('was-validated');
      return;
    }

    // submit form
    const data = new FormData(e.target);
    const formData = Object.fromEntries(data.entries());
    formData.customerId = selectedCustomerId;
    formData.initialCredit = Number(formData.initialCredit);
    console.log(formData);
    handleCreateAccFormSubmit(formData);
  });
};

init();
