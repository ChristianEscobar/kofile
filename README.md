# kofile
A REST API

FEES:

The file fees.json describes fees that are applied to each of the items in an order. 
Each type of item in an order can have one or more fees associated with it. 
The total cost for the order item is the aggregate of all of fees associated with that item. 
There are different types of fees. Flat fees are simply a single charge. 
Per-page fees add an additional fee on top of a flat fee for each page after the first.

DISTRIBUTIONS:

Each fee for each order item has a set of funds associated with it. 
The money associated with each fee is split among the funds based on the amount specified in the distribution. 
Any extra money associated with the order that isn't allocated to a fund should be assigned to a generic "Other" fund.

PROJECT DESCRIPTION:

This project implements a RESTful API using JAX-RS.  The API has the following endpoints:  
1.  /orders/orderprices 
2.  /orders/distributiontotals

The first endpoint takes an array of orders and returns a JSON structure containing the prices for each order item 
and the total for the order. 

The second endpoint takes an array of orders and returns a JSON structure containing the distribution totals for each order. 

The fees are contained in the following file:  src/main/resources/fees.json which is automatically read by the API.

SAMPLE ORDERS ARRAY (This is the input to provide to each endpoint):

[
  {
    "order_date": "1/11/2015",
    "order_number": "20150111000001",
    "order_items": [
      {
        "order_item_id": 1,
        "type": "Real Property Recording",
        "pages": 3
      },
      {
        "order_item_id": 2,
        "type": "Real Property Recording",
        "pages": 1
      }
    ]
  },
  {
    "order_date": "1/17/2015",
    "order_number": "20150117000001",
    "order_items": [
      {
        "order_item_id": 3,
        "type": "Real Property Recording",
        "pages": 2
      },
      {
        "order_item_id": 4,
        "type": "Real Property Recording",
        "pages": 20
      }
    ]
  },
  {
    "order_date": "1/18/2015",
    "order_number": "20150118000001",
    "order_items": [
      {
        "order_item_id": 5,
        "type": "Real Property Recording",
        "pages": 5
      },
      {
        "order_item_id": 6,
        "type": "Birth Certificate",
        "pages": 1
      }
    ]
  },
  {
    "order_date": "1/23/2015",
    "order_number": "20150123000001",
    "order_items": [
      {
        "order_item_id": 7,
        "type": "Birth Certificate",
        "pages": 1
      },
      {
        "order_item_id": 8,
        "type": "Birth Certificate",
        "pages": 1
      }
    ]
  }
