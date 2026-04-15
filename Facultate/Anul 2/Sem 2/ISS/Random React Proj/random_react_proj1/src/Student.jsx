import PropTypes from 'prop-types'

function Student(props){
    return (
        <div className = "font-sans text-lg p-3 m-2 border border-gray-500 space-y-0">
            <p>Name: {props.name}</p>
            <p>Age: {props.age}</p>
            <p>Student: {props.isStudent ? "Yes" : "No"}</p>
        </div>

    );
}

Student.propTypes = {
    name: PropTypes.string,
    age: PropTypes.number,
    isStudent: PropTypes.bool
}
export default Student;